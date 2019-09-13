(function () {
    'use strict';

    angular
        .module('researchRepositoryLearningSystemApp')
        .controller('OpportunityMasterDetailController', OpportunityMasterDetailController);

    OpportunityMasterDetailController.$inject = ['$scope', '$rootScope', 'Principal', '$stateParams', 'previousState', 'entity', 'OpportunityMaster', 'StrategyMaster', 'Upload', 'FileUploadComments', 'FileUpload', '$uibModal', '$filter', '$http', 'OpportunityQuestion', 'DecimalConfiguration', 'CommentOpportunity', '$location', '$q'];

    function OpportunityMasterDetailController($scope, $rootScope, Principal, $stateParams, previousState, entity, OpportunityMaster, StrategyMaster, Upload, FileUploadComments, FileUpload, $uibModal, $filter, $http, OpportunityQuestion, DecimalConfiguration, CommentOpportunity, $location, $q) {
        var vm = this;
        vm.AdminFiles = false;
        vm.opportunityMaster = entity;
        console.log('opportunityMaster ');
        console.log(vm.opportunityMaster);
        vm.previousState = previousState.name;
        vm.opportunityMaster.financialSummaryData = vm.opportunityMaster.financialSummaryData ? vm.opportunityMaster.financialSummaryData : null;
        vm.opportunityMaster.nonFinancialSummaryData = vm.opportunityMaster.nonFinancialSummaryData ? vm.opportunityMaster.nonFinancialSummaryData : null;
        // vm.opportunityMaster.financialSummaryData = vm.opportunityMaster.financialSummaryData ? vm.opportunityMaster.financialSummaryData : null;
        // vm.opportunityMaster.financialSummaryData = {};
        vm.readOnly = true;
        vm.upload = upload;
        vm.selectFile = selectFile;
        vm.saveComment = saveComment;
        vm.load = load;
        vm.loadFileContent = loadFileContent;
        vm.loadFilePreview = loadFilePreview;
        vm.loadFileExternal = loadFileExternal;
        vm.loadFileCommunication = loadFileCommunication;
        vm.fileId = '';
        vm.createFile = createFile;
        vm.approveFile = approveFile;
        vm.aStatus = '';
        /*vm.additionalFile=additionalFile;*/
        vm.addFileName = '';
        vm.summaryData = '';
        vm.questions = [];
        vm.account = null;
        vm.decimalValue = null;
        vm.mydisable = true;
        vm.googleSheetLink = '';
        vm.googleSheetData = [];
        vm.googleSheetHeading = [];
        vm.subHeading = [];
        vm.previewFileExtension = '';
        vm.googleHeading = false;
        // vm.fileDelete=fileDelete;
        vm.communicationFileDelete = communicationFileDelete;
        vm.confidentialFileDelete = confidentialFileDelete;
        vm.dueDiligenceDelete = dueDiligenceDelete;
        vm.externalFileDelete = externalFileDelete;
        vm.raFileDelete = raFileDelete;
        vm.getOpportunityMaster = getOpportunityMaster;
        vm.popUpURL = '';
        vm.tab = 1;
//vm.setTab=setTab;
//vm.isSet=isSet;
        vm.sheets = []


        console.log('Decimal value', vm.opportunityMaster.decimalPoint);
        //  vm.submiTable=submitTable;
        $scope.$on('authenticationSuccess', function () {
            getAccount();
        });

        getAccount();
        setTimeout(function () {
            getDecimalConfig();
        }, 3000);

        function getAccount() {
            console.log('get Account invoked');
            Principal.identity().then(function (account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
                if (vm.account.authorities[1] == 'Research' || vm.account.authorities[1] == 'CIO') {
                    vm.mydisable = false;
                }
                for (var i = 0; i < vm.account.authorities.length; i++) {
                    if (vm.account.authorities[i] == 'Admin' || (vm.account.firstName == 'Sarath' && vm.account.authorities[i] == 'CIO') || (vm.account.firstName == 'Baidik' && vm.account.authorities[i] == 'Research')) {
                        vm.AdminFiles = true;
                        $scope.$apply();
                        // console.log('vm.AdminFiles' + vm.AdminFiles);
                    } else {
                        // vm.AdminFiles = false;
                        vm.AdminFiles = false;
                    }
                }


            });

        }


        function getDecimalConfig() {
            DecimalConfiguration.get({id: vm.account.id}, function (resp) {

                if (vm.account.login == vm.opportunityMaster.createdBy) {
                    vm.decimalValue = resp.decimalValue;
                }
            }, function (err) {
                console.log(err);
            });

        }


        var unsubscribe = $rootScope.$on('researchRepositoryLearningSystemApp:opportunityMasterUpdate', function (event, result) {
            vm.opportunityMaster = result;
        });
        $scope.$on('$destroy', unsubscribe);

        // Editor options.
        $scope.options = {
            language: 'en',
            allowedContent: true,
            entities: false
        };

        // Called when the editor is completely ready.
        $scope.onReady = function () {
            vm.readOnly = false;
        };

        // $scope.myVar = false;
        $scope.showdocument = function () {
            $scope.myvar = !$scope.myvar;
            /* $scope.addFile = !$scope.addFile;*/

        };

        $scope.fileformat = ["Presentation", "Excel Model", "Quarterly Updates","Risk","Miscellaneous"];

        $scope.getselectval = function () {

        };

        var myDate = new Date();
        var previousYear = new Date(myDate);
        previousYear.setYear(myDate.getFullYear() - 1);
        var previousYearBefore = new Date(previousYear);
        previousYearBefore.setYear(previousYear.getFullYear() - 1);
        var nextYear = new Date(myDate);
        nextYear.setYear(myDate.getFullYear() + 1);
        var nextYearNext = new Date(nextYear);
        nextYearNext.setYear(nextYear.getFullYear() + 1);
        $scope.currentYear = $filter('date')(myDate, 'yyyy');
        $scope.nextYear = $filter('date')(nextYear, 'yyyy');
        $scope.prevYear = $filter('date')(previousYear, 'yyyy');
        $scope.prevYearBefore = $filter('date')(previousYearBefore, 'yyyy');
        $scope.neYearNext = $filter('date')(nextYearNext, 'yyyy');

        $scope.submitTable = function () {
            console.log('vm.opportunityMaster');
            console.log(vm.opportunityMaster);
            OpportunityMaster.summarydatavalues(vm.opportunityMaster, function (resp) {
                $scope.isDisabled = true;
            }, function (err) {
                console.log(err);
            });

        }

        $scope.ok = function () {

            $scope.isDisabled = false;

        };

        $scope.cancel = function () {

            $scope.isDisabled = true;

        };

        $scope.updateSum = function () {
            $scope.sumVal = ($scope.vm.opportunityMaster.financialSummaryData.netIntOne * 1) + ($scope.vm.opportunityMaster.financialSummaryData.nonIntOne * 1);
        }

        $scope.getTotal = function (val1, val2, val3) {
            // console.log(val1, val2, val3);
            val1 = isNaN(val1) ? 0 : val1;
            val2 = isNaN(val2) ? 0 : val2;
            var result = parseFloat(val1) + parseFloat(val2);

            //result=(isNaN(result) || result==Infinity) ? 0:result;

            switch (val3) {
                case 1:
                    vm.opportunityMaster.financialSummaryData.totIncOne = result;
                    break;
                case 2:
                    vm.opportunityMaster.financialSummaryData.totIncTwo = result;
                    break;
                case 3:
                    vm.opportunityMaster.financialSummaryData.totIncThree = result;
                    break;
                case 4:
                    vm.opportunityMaster.financialSummaryData.totIncFour = result;
                    break;
                case 5:
                    vm.opportunityMaster.financialSummaryData.totIncFive = result;
                    break;
                default:
                    break;
            }


            return result;
        };
        $scope.getFinPbv = function (val1, val2, val3) {

            var result = (parseFloat(val1) / parseFloat(val2));
            result = (isNaN(result) || result == Infinity) ? 0 : result;
            if (result != '') {
                switch (val3) {
                    case 1:
                        vm.opportunityMaster.financialSummaryData.pbvOne = result;
                        break;
                    case 2:
                        vm.opportunityMaster.financialSummaryData.pbvTwo = result;
                        break;
                    case 3:
                        vm.opportunityMaster.financialSummaryData.pbvThree = result;
                        break;
                    case 4:
                        vm.opportunityMaster.financialSummaryData.pbvFour = result;
                        break;
                    case 5:
                        vm.opportunityMaster.financialSummaryData.pbvFive = result;
                        break;
                    case 6:
                        vm.opportunityMaster.financialSummaryData.peOne = result;
                        break;
                    case 7:
                        vm.opportunityMaster.financialSummaryData.peTwo = result;
                        break;
                    case 8:
                        vm.opportunityMaster.financialSummaryData.peThree = result;
                        break;
                    case 9:
                        vm.opportunityMaster.financialSummaryData.peFour = result;
                        break;
                    case 10:
                        vm.opportunityMaster.financialSummaryData.peFive = result;
                        break;
                    default:
                        break;
                }
            }
            return result;
        };
        $scope.getFinRoe = function (val1, val2, val3, val4) {
            if (val2 == 0) {
                var result = parseFloat(val1) / parseFloat(val3);
                result = (result * 100);
            } else {
                var result = parseFloat(val1) / ((parseFloat(val2) + parseFloat(val3)) / 2);
                result = (result * 100);
            }
            result = (isNaN(result) || result == Infinity) ? 0 : result;
            if (result != '') {
                switch (val4) {
                    case 1:
                        vm.opportunityMaster.financialSummaryData.roeOne = result;

                        break;
                    case 2:
                        vm.opportunityMaster.financialSummaryData.roeTwo = result;
                        break;
                    case 3:
                        vm.opportunityMaster.financialSummaryData.roeThree = result;
                        break;
                    case 4:
                        vm.opportunityMaster.financialSummaryData.roeFour = result;
                        break;
                    case 5:
                        vm.opportunityMaster.financialSummaryData.roeFive = result;
                        break;
                    default:
                        break;
                }
            }
            return result;
        };
        $scope.getNonGrowth = function (val1, val2, val3) {

            var result = (parseFloat(val2) / parseFloat(val1)) - 1;
            result = (result * 100);
            result = (isNaN(result) || result == Infinity) ? 0 : result;
            if (result != '') {
                switch (val3) {
                    case 1:
                        vm.opportunityMaster.nonFinancialSummaryData.revGrowthTwo = result;
                        break;
                    case 2:
                        vm.opportunityMaster.nonFinancialSummaryData.revGrowthThree = result;
                        break;
                    case 3:
                        vm.opportunityMaster.nonFinancialSummaryData.revGrowthFour = result;
                        break;
                    case 4:
                        vm.opportunityMaster.nonFinancialSummaryData.revGrowthFive = result;
                        break;
                    case 5:
                        vm.opportunityMaster.nonFinancialSummaryData.ebitdaGrowthTwo = result;
                        break;
                    case 6:
                        vm.opportunityMaster.nonFinancialSummaryData.ebitdaGrowthThree = result;
                        break;
                    case 7:
                        vm.opportunityMaster.nonFinancialSummaryData.ebitdaGrowthFour = result;
                        break;
                    case 8:
                        vm.opportunityMaster.nonFinancialSummaryData.ebitdaGrowthFive = result;
                        break;
                    case 9:
                        vm.opportunityMaster.nonFinancialSummaryData.patGrowthTwo = result;
                        break;
                    case 10:
                        vm.opportunityMaster.nonFinancialSummaryData.patGrowthThree = result;
                        break;
                    case 11:
                        vm.opportunityMaster.nonFinancialSummaryData.patGrowthFour = result;
                        break;
                    case 12:
                        vm.opportunityMaster.nonFinancialSummaryData.patGrowthFive = result;
                        break;
                    default:
                        break;
                }
            }
            return result;
        };
        $scope.getNonMargin = function (val1, val2, val3) {
            val1 = (isNaN(val1)) ? null : val1;
            val2 = (isNaN(val2)) ? null : val2;
            //console.log('EBITDA',val1,val2,val3);
            var result = parseFloat(val1) / parseFloat(val2);
            result = (result * 100);
            result = (isNaN(result) || result == Infinity) ? 0 : result;
            //            console.log('result',result);

            switch (val3) {
                case 1:
                    vm.opportunityMaster.nonFinancialSummaryData.marginOne = result;
                    if (val1 == null || val1 == null) {
                        vm.opportunityMaster.nonFinancialSummaryData.marginOne = 0;
                        result == 0;
                    }
                    break;
                case 2:
                    vm.opportunityMaster.nonFinancialSummaryData.marginTwo = result;
                    break;
                case 3:
                    vm.opportunityMaster.nonFinancialSummaryData.marginThree = result;
                    break;
                case 4:
                    vm.opportunityMaster.nonFinancialSummaryData.marginFour = result;
                    break;
                case 5:
                    vm.opportunityMaster.nonFinancialSummaryData.marginFive = result;
                    break;
                default:
                    break;
            }

            /*if(result=='') {
                console.log("result else ");
                result = 0;
                console.log('val 3',val3);
                return result;
            }	*/
            return result;

        };
        $scope.getNonPbt = function (val1, val2, val3, val4, val5) {

            var result = (parseFloat(val1) + parseFloat(val2) - parseFloat(val3)
                - parseFloat(val4));
            result = (isNaN(result) || result == Infinity) ? 0 : result;
            if (result != '') {
                switch (val5) {
                    case 1:
                        vm.opportunityMaster.nonFinancialSummaryData.pbtOne = result;
                        break;
                    case 2:
                        vm.opportunityMaster.nonFinancialSummaryData.pbtTwo = result;
                        break;
                    case 3:
                        vm.opportunityMaster.nonFinancialSummaryData.pbtThree = result;
                        break;
                    case 4:
                        vm.opportunityMaster.nonFinancialSummaryData.pbtFour = result;
                        break;
                    case 5:
                        vm.opportunityMaster.nonFinancialSummaryData.pbtFive = result;
                        break;
                    default:
                        break;
                }
            }
            return result;
        };
        $scope.getNonPat = function (val1, val2, val4, val5, val3) {

            var result = (parseFloat(val1) + parseFloat(val2) + parseFloat(val4) - parseFloat(val5));
            result = (isNaN(result) || result == Infinity) ? 0 : result;
            if (result != '') {
                switch (val3) {
                    case 1:
                        vm.opportunityMaster.nonFinancialSummaryData.patOne = result;
                        break;
                    case 2:
                        vm.opportunityMaster.nonFinancialSummaryData.patTwo = result;
                        break;
                    case 3:
                        vm.opportunityMaster.nonFinancialSummaryData.patthree = result;
                        break;
                    case 4:
                        vm.opportunityMaster.nonFinancialSummaryData.patfour = result;
                        break;
                    case 5:
                        vm.opportunityMaster.nonFinancialSummaryData.patFive = result;
                        break;
                    default:
                        break;
                }
            }
            return result;
        };
        $scope.getNonPe = function (val1, val2, val3) {

            var result = (parseFloat(val1) / parseFloat(val2));
            result = (isNaN(result) || result == Infinity) ? 0 : result;
            if (result != '') {
                switch (val3) {
                    case 1:
                        vm.opportunityMaster.nonFinancialSummaryData.peOne = result;
                        break;
                    case 2:
                        vm.opportunityMaster.nonFinancialSummaryData.peTwo = result;
                        break;
                    case 3:
                        vm.opportunityMaster.nonFinancialSummaryData.pethree = result;
                        break;
                    case 4:
                        vm.opportunityMaster.nonFinancialSummaryData.peFour = result;
                        break;
                    case 5:
                        vm.opportunityMaster.nonFinancialSummaryData.peFive = result;
                        break;
                    case 6:
                        vm.opportunityMaster.nonFinancialSummaryData.pbOne = result;
                        break;
                    case 7:
                        vm.opportunityMaster.nonFinancialSummaryData.pbTwo = result;
                        break;
                    case 8:
                        vm.opportunityMaster.nonFinancialSummaryData.pbThree = result;
                        break;
                    case 9:
                        vm.opportunityMaster.nonFinancialSummaryData.pbFour = result;
                        break;
                    case 10:
                        vm.opportunityMaster.nonFinancialSummaryData.pbFive = result;
                        break;
                    case 11:
                        vm.opportunityMaster.nonFinancialSummaryData.deOne = result;
                        break;
                    case 12:
                        vm.opportunityMaster.nonFinancialSummaryData.deTwo = result;
                        break;
                    case 13:
                        vm.opportunityMaster.nonFinancialSummaryData.deThree = result;
                        break;
                    case 14:
                        vm.opportunityMaster.nonFinancialSummaryData.deFour = result;
                        break;
                    case 15:
                        vm.opportunityMaster.nonFinancialSummaryData.deFive = result;
                        break;
                    default:
                        break;
                }
            }
            return result;

        };
        $scope.getNonRoe = function (val1, val2, val3, val4) {

            if (val2 == 0) {
                var result = parseFloat(val1) / parseFloat(val3);
                result = (result * 100);
            } else {
                var result = parseFloat(val1)
                    / ((parseFloat(val2) + parseFloat(val3)) / 2);
                result = (result * 100);
            }
            result = (isNaN(result) || result == Infinity) ? 0 : result;
            if (result != '') {
                switch (val4) {
                    case 1:

                        vm.opportunityMaster.nonFinancialSummaryData.roeOne = result;
                        break;
                    case 2:
                        vm.opportunityMaster.nonFinancialSummaryData.roeTwo = result;
                        break;
                    case 3:
                        vm.opportunityMaster.nonFinancialSummaryData.roeThree = result;
                        break;
                    case 4:
                        vm.opportunityMaster.nonFinancialSummaryData.roeFour = result;
                        break;
                    case 5:
                        vm.opportunityMaster.nonFinancialSummaryData.roefive = result;
                        break;
                    default:
                        break;
                }
            }
            return result;
        };
        $scope.getNonTaxRat = function (val1, val2, val3) {

            var result = parseFloat(val1) / parseFloat(val2);
            result = (result * 100);
            result = (isNaN(result) || result == Infinity) ? 0 : result;
            if (result != '') {
                switch (val3) {
                    case 1:
                        vm.opportunityMaster.nonFinancialSummaryData.taxRateOne = result;
                        break;
                    case 2:
                        vm.opportunityMaster.nonFinancialSummaryData.taxRateTwo = result;
                        break;
                    case 3:
                        vm.opportunityMaster.nonFinancialSummaryData.taxRateThree = result;
                        break;
                    case 4:
                        vm.opportunityMaster.nonFinancialSummaryData.taxRateFour = result;
                        break;
                    case 5:
                        vm.opportunityMaster.nonFinancialSummaryData.taxRateFive = result;
                        break;
                    default:
                        break;
                }
            }
            return result;
        };
        $scope.getNonIntRat = function (val1, val2, val3) {

            var result = parseFloat(val1) / parseFloat(val2);
            result = (result * 100);
            result = (isNaN(result) || result == Infinity) ? 0 : result;
            if (result != '') {
                switch (val3) {
                    case 1:
                        vm.opportunityMaster.nonFinancialSummaryData.intRateOne = result;
                        break;
                    case 2:
                        vm.opportunityMaster.nonFinancialSummaryData.intRateTwo = result;
                        break;
                    case 3:
                        vm.opportunityMaster.nonFinancialSummaryData.intRateThree = result;
                        break;
                    case 4:
                        vm.opportunityMaster.nonFinancialSummaryData.intRateFour = result;
                        break;
                    case 5:
                        vm.opportunityMaster.nonFinancialSummaryData.intRateFive = result;
                        break;
                    default:
                        break;
                }
            }
            return result;
        };
        $scope.isDisabled = true;

        $scope.open = function (status) {

            var modalInstance = $uibModal.open({
                templateUrl: 'app/entities/opportunity-master/opportunity-description-dialog.html',
                controllerAs: '$ctrl',
                controller: function () {
                    this.message = 'some message';

                    this.ok = function () {
                        var val = this.description;

                        OpportunityMaster.description({
                            statusDes: val,
                            id: vm.opportunityMaster.id,
                            oppStatus: status
                        }, function (resp) {

                            vm.opportunityMaster.oppStatus = resp.oppStatus;
                        }, function (err) {
                            console.log(err);
                        });
                        var inputData = {};

                        inputData.commentText = val + " - " + status;
                        inputData.opportunityMaster = vm.opportunityMaster;

                        CommentOpportunity.save(inputData, function (resp) {
                                /* vm.opportunityMaster.fileUploadCommentList.push(resp);*/
                            },
                            function (err) {
                                console.log(err);
                            });
                        modalInstance.close();
                    };

                    this.cancel = function () {
                        modalInstance.dismiss('cancel');
                    };
                },
                resolve: {}
            });


        };

        $scope.question = function () {

            var modalInstance = $uibModal.open({
                templateUrl: 'app/entities/opportunity-master/opportunity-question-answer.html',
                controllerAs: '$ctrl',
                controller: 'OpportunityQuestionController',
                size: 'lg',
                resolve: {
                    options: function () {
                        return vm.opportunityMaster;
                    }
                }
            });


        };

        $scope.learning = function () {

            var modalInstance = $uibModal.open({
                templateUrl: 'app/entities/opportunity-learning/opportunity-master-learning.html',
                controllerAs: '$ctrl',
                controller: 'OpportunityLearningController',
                size: 'lg',
                resolve: {
                    options: function () {
                        return vm.opportunityMaster;
                    }
                }
            });


        };
        $scope.learningAIF = function () {

            var modalInstance = $uibModal.open({
                templateUrl: 'app/entities/opportunity-master-learn-aif/opportunity-master-learn-aif.html',
                controllerAs: '$ctrl',
                controller: 'OpportunityLearningAIFController',
                size: 'lg',
                resolve: {
                    options: function () {
                        return vm.opportunityMaster;
                    }
                }
            });


        };

        $scope.commentstemp = function () {
            var modalInstance = $uibModal.open({
                templateUrl: 'app/entities/opportunity-master/opportunity-comment.html',
                controllerAs: '$ctrl',
                controller: 'OpportunityCommentController',
                size: 'lg',
                resolve: {
                    options: function () {
                        return vm.opportunityMaster;
                    }
                }

            });

        }


        function load() {
            /*vm.opportunityMaster.fileUploadCommentList = OpportunityMaster.get({id : $stateParams.id}, function(resp){
             console.log(resp);*/
            vm.opportunityMaster.fileUploadCommentList;
            vm.opportunityMaster.status;

            /* }, function(err) {
              console.log(err);
          });*/
        }

        function clear() {
            vm.newdoc = "";
            $scope.myvar = false;
            vm.fileName = "";
            /*vm.addNewdoc="";
            $scope.addFile = false;
            vm.htmlContent="";*/
        }

        function upload() {
            console.log('uploading....');
            var selectitem = $scope.selitem;
            vm.isSaving = true;


            Upload.upload({
                url: 'api/file-uploads',
                data: {fileUploads: vm.opportunityMaster.fileUpload},
                params: {oppId: vm.opportunityMaster.id, filetype: selectitem, uploadfileName: vm.uploadfileName}// {oppCode: inputData.oppCode, oppName: inputData.oppName, oppDescription: inputData.oppDescription, strategyMasterId: inputData.strategyMasterId.id}
            }).then(function (resp) {

                console.log(resp);

                if (resp.status == 201) {
                    if (vm.opportunityMaster.fileUploads == null) {
                        vm.opportunityMaster.fileUploads = [];

                    }
                    //   console.log("gashgdjgasudgua",vm.opportunityMaster.fileUploads);

                    vm.opportunityMaster.fileUploads.push(resp.data);
                }
            }, function (resp) {
                console.log(resp);
            }, function (evt) {
                console.log(evt);

            });
        }

        function selectFile(file) {


            vm.opportunityMaster.fileUpload = file;
        }

        function createFile() {
            var doc = "<body>" + vm.newdoc + "</body>";
            OpportunityMaster.wordCreation({
                fileContent: doc, fileName: vm.fileName, oppId: vm.opportunityMaster.id,
                oppName: vm.opportunityMaster.masterName.oppName, oppId: vm.opportunityMaster.id
            }, function (result) {
                vm.opportunityMaster.fileUploads.push(result);
                clear();
            }, onSaveError);
        }

        function saveComment() {
            vm.isSaving = true;
            var inputData = {};
            inputData.opportunityComments = vm.fileComments;
            inputData.opportunityMaster = vm.opportunityMaster;

            FileUploadComments.save(inputData, function (resp) {
                vm.opportunityMaster.fileUploadCommentList.push(resp);
                vm.fileComments = "";
            }, onSaveError);

        }

        function approveFile(status) {
            vm.opportunityMaster.oppStatus = status;

            OpportunityMaster.update(vm.opportunityMaster, function (resp) {

            }, function (err) {
                console.log(err);
            });

        }

        function loadFileContent(fileID) {
            window.open('/download/' + fileID, '_blank');
        }


        function loadFilePreview(fileName) {
            console.log('file preview function invoked');
            var data = fileName;
            data = data.replace(/\\/g, "/");
            console.log('After slash replace - ' + data);

            let urlValues = data.split('/');
            for (var i = 0; i < urlValues.length; i++) {
                if (urlValues[i].endsWith('.')) {
                    urlValues[i] = urlValues[i].slice(0, -1);
                }
            }
            console.log(urlValues);
            data = urlValues.join('/');
            console.log('After dot replace - ' + data);
            var url = window.location.origin + data.split('webapp')[1];
            console.log("url -" + url);
            window.open(url, '_blank');

            //after replace
            // var data='src/main/webapp/content/fileUpload/Aarti Inds./saravanan/Aarti Q3__9M_FY_18_Results_Presentation.pdf';
            // var data='src/main/webapp/content/fileUpload/Communication/Kirloskar Brothers Ltd/sreemant-Questions to Management for Meeting.Copy/Letter to Mr. Sanjay Kirloskar.pdf';
            // var data='src/main/webapp/content/fileUpload/Communication/Kirloskar Brothers Ltd/sreemant-Questions to Management for Meeting/Letter to Mr. Sanjay Kirloskar.pdf';
            // var data='src/main/webapp/content/fileUpload/Kirloskar Indus/sreedevi/Kirloskar Industries..xlsx';

            // var dataArr=data.split('/');
            // console.log(dataArr);
            // var fileName=dataArr[dataArr.length-1];
            // console.log('fileName -'+fileName);
            // console.log(dataArr);
            // data = data.replace("./", "/");
            // var url = window.location.origin + data.split('webapp')[1];
            //             console.log("url -" + url);
            //             window.open(url, '_blank');

        }

        function loadFileExternal(fileID) {

            window.open('/download-external/' + fileID, '_blank');

        }

        function loadFileCommunication(fileID) {
            console.log('fileID comm -');

            console.log(fileID);

            window.open('/download-communication/' + fileID, '_blank');

        }


        function onSaveSuccess(result) {
            vm.isSaving = false;
            vm.opportunityMaster.fileUploadCommentList.push(result);
            vm.fileComments = "";

        }

        function onSaveError() {
            vm.isSaving = false;
        }


        $scope.imageUpload = function (event) {
            var files = event.target.files;

            for (var i = 0; i < files.length; i++) {
                var file = files[i];
                var reader = new FileReader();
                reader.onload = $scope.imageIsLoaded;
                reader.readAsDataURL(file);
            }
        }

        $scope.imageIsLoaded = function (e) {
            $scope.$apply(function () {
                $scope.img = e.target.result;
            });
        }

        function getOpportunityMaster() {
            OpportunityMaster.get({id: $stateParams.id}, function (result) {
                vm.opportunityMaster = result;
                console.log('Refresh after file delete');
                console.log(vm.opportunityMaster);
            });
        }

        function raFileDelete(file) {
            console.log('File for ra Deletion');
            console.log(file);
            return $http.delete('/api/file-uploads/' + file.id).then(
                function (response) {
                    console.log('response in delete ra file');
                    console.log(response);
                    vm.getOpportunityMaster();
                }).catch(function (error) {
                console.log(error);
            });
        }

        function externalFileDelete(file) {
            console.log('File for external Deletion ');
            console.log(file);
            return $http.delete('/api/externalRA/' + file.id).then(
                function (response) {
                    console.log('response in delete external');
                    console.log(response);
                    vm.getOpportunityMaster();
                }).catch(function (error) {
                console.log(error);
            });
        }

        function communicationFileDelete(file) {
            console.log('File for communication Deletion');
            console.log(file);
            console.log(file.id);
            return $http.delete('/api/communication-letter/' + file.id).then(
                function (response) {
                    console.log('response in communication file delete');
                    console.log(response);
                    vm.getOpportunityMaster();
                }).catch(function (error) {
                console.log(error);
            });
        }

        function confidentialFileDelete(file) {
            console.log('File for confidential Deletion');
            console.log(file);
            return $http.delete('/api/confidential-letter/' + file.id).then(
                function (response) {
                    console.log('response in confidential file delete');
                    console.log(response);
                    vm.getOpportunityMaster();
                }).catch(function (error) {
                console.log(error);
            })
        }

        function dueDiligenceDelete(file) {
            console.log('File for due diligence Deletion');
            console.log(file);
            return $http.delete('/api/due-diligence/' + file.id).then(
                function (response) {
                    console.log('response in due diligence file delete');
                    console.log(response);
                    vm.getOpportunityMaster();
                }).catch(function (error) {
                console.log(error);
            })
        }

        $scope.getDataFromGSheet = function () {
            vm.googleSheetData = [];
            // vm.subHeading = [];
            var jsonObj = {};
            var gsID = vm.googleSheetLink;
            // var gsID = document.getElementById('googleSheetLink').value;
            gsID = gsID.match(/[-\w]{25,}/)[0];
            var url = "https://spreadsheets.google.com/feeds/list/" + gsID + "/1/public/values?alt=json";
            console.log('url -' + url);
            var parse = function (entry) {
                console.log('entry');
                console.log(entry.content);
                jsonObj = {}, vm.googleSheetHeading = [];
                var gsColumns = Object.keys(entry).filter(function (columnValue) {
                    return columnValue.indexOf("gsx$") === 0;
                });
                for (var i = 0; i < gsColumns.length; i++) {
                    var value = gsColumns[i].split('gsx$')[1];
                    // console.log('value -' + value);
                    // if(!value.startsWith('_')){
                    if (!vm.googleSheetHeading.includes(value)) {
                        vm.googleSheetHeading.push(value);
                    }
                    // }

                }
                console.log('vm.googleSheetHeading');
                console.log(vm.googleSheetHeading);

                var length = vm.googleSheetHeading.length;
                for (var i = 0; i < gsColumns.length; i++) {
                    // console.log('value -' + entry[gsColumns[i]]['$t']);
                    if (entry[gsColumns[i]]['$t']) {
                        jsonObj[vm.googleSheetHeading[i]] = entry[gsColumns[i]]['$t'];
                    } else {
                        jsonObj[vm.googleSheetHeading[i]] = '';
                    }

                }
                vm.googleSheetData.push(jsonObj);
                // $scope.$apply();
                // return jsonObj;
            }

            $.ajax({
                url: url,
                dataType: "jsonp",
                success: function (data) {
                    console.log('data in gs');
                    var entries = data['feed']['entry'];
                    for (var key in entries) {
                        var content = entries[key];
                        parse(content);

                    }
                    console.log("Length of sheet - " + vm.googleSheetData.length);
                    for (var i = 0; i < vm.googleSheetHeading.length; i++) {
                        console.log(' _ Available in heading ' + vm.googleSheetHeading[i].startsWith('_'));
                        if (vm.googleSheetHeading[i].startsWith('_')) {
                            // vm.subHeading = [];
                            vm.subHeading = vm.googleSheetData[0];
                            console.log('vm.subHeading');
                            console.log(vm.subHeading);
                            delete vm.googleSheetData[0];
                            console.log('vm.googleSheetData');
                            console.log(vm.googleSheetData);
                            vm.googleSheetHeading = vm.subHeading;
                            vm.googleHeading = true;
                            break;
                        } else {
                            vm.googleHeading = false;
                        }
                    }
                    $scope.$apply();
                },
                error: function (xhr, status, error) {
                    console.log('error in gs');
                    console.log(error);
                    console.log(status);
                    console.log(xhr);
                    var currentLoc = $location.$$absUrl;
                    console.log('currentLoc -');
                    console.log(currentLoc);
                    var win = window.open(url, " Need to login", 'width=600,height=600');
                    function checkURL() {
                        if(win.location==url){
                            win.close();
                        }else{
                            setInterval(checkURL, 2000);
                        }
                    }
                    if(win){
                        console.log("inside if condition");
                        console.log(win.location);
                        if(win.location==url){
                            win.close();
                        }else{
                            setInterval(checkURL, 2000);
                        }
                    }


                   /** history.pushState({
                        prevUrl: currentLoc
                    }, 'Next page', url);
                    history.go();
                    if ($location.$$absUrl==url){
                        var link = history.state.prevUrl;
                        if (link) {
                            console.log('user come from: ' + link)
                        }

                    }**/
                }
            });
        }

        $scope.GSV4 = function () {
            vm.googleSheetData = [];
            var jsonObj = {}, range = '';
            var selectedOption = $("#selectedSheets option:selected");

            function hasWhiteSpace(s) {
                return /\s/g.test(s);
            }

            if (selectedOption.length > 0) {
                for (var i = 0; i < selectedOption.length; i++) {
                    var value = selectedOption[i].text;
                    if (i == 0) {

                        if (hasWhiteSpace(value)) {
                            //                   console.log(' contains space');
                            range = "ranges=" + encodeURIComponent(value) + "&";

                        } else {
                            //console.log(' not contains space');
                            range = "ranges=" + value + "&";
                        }
                    } else {
                        if (hasWhiteSpace(value)) {
                            console.log(" contains space");
                            range += "ranges=" + encodeURIComponent(value) + "&";

                        } else {
                            //console.log(" not contains space");
                            range += "ranges=" + value + "&";
                        }
                    }

                }

                /*     console.log('range');
                                     console.log(range);*/
            }
            var gsID = vm.googleSheetLink;
            gsID = gsID.match(/[-\w]{25,}/)[0];
            var url = "https://sheets.googleapis.com/v4/spreadsheets/" + gsID + "/values:batchGet?" + range + "key=AIzaSyDQ7P-C57y88QaUmifTpMupUOM-Vt34wAU";
            console.log('url -' + url);
            $.ajax({
                url: url,
                dataType: "jsonp",
                success: function (data) {
                    console.log('success in gs');
                    console.log(data);
                    var allSheets = data.valueRanges;
                    for (var i = 0; i < allSheets.length; i++) {
                        var sheet = {sheetName: '', values: []};
                        var sheetName = allSheets[i].range;
                        sheet.sheetName = sheetName.split('!')[0];
                        sheet.values = allSheets[i].values;
                        if (sheet.values) {
                            sheet.heading = [];
                            sheet.heading.push(sheet.values[0]);
                            sheet.heading.push(sheet.values[1]);
                            delete sheet.values[0];
                            delete sheet.values[1];
                        }

                        /*     angular.forEach(sheet.values, function(value, index) {
                             if (value.length < sheet.heading[1].length) {
                                *//* sheet.values[index].fill(" ", value.length, sheet.heading[1].length);
                            console.log(value);*//*

                            var td = document.createElement('td'); // create a td node
                                                        td.innerHTML =" "; // fill the td now with a piece of the data array
                                                        document.getElementById("valuationSheet").appendChild(td);
                        }
                        });*/


                        vm.sheets.push(sheet);
                    }
                    console.log(vm.sheets);
                    console.log(vm.sheets.length);
                    $scope.$apply();
                },
                error: function (xhr, status, error) {
                    console.log('error in gs');
                    console.log(error);
                    AlertService.error(error.message);
                }
            });

        }
        $scope.setTab = function (tabId) {
            vm.tab = tabId;
        };

        $scope.isSet = function (tabId) {
            return vm.tab === tabId;
        };


    }

})();


