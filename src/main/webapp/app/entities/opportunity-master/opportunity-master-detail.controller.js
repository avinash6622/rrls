(function() {
    'use strict';

    angular
        .module('researchRepositoryLearningSystemApp')
        .controller('OpportunityMasterDetailController', OpportunityMasterDetailController);

    OpportunityMasterDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'OpportunityMaster', 'StrategyMaster', 'Upload', 'FileUploadComments','FileUpload','$uibModal'];

    function OpportunityMasterDetailController($scope, $rootScope, $stateParams, previousState, entity, OpportunityMaster, StrategyMaster, Upload, FileUploadComments,FileUpload,$uibModal) {
        var vm = this;

        vm.opportunityMaster = entity;
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
        vm.fileId='';
        vm.createFile = createFile;
        vm.approveFile=approveFile;
        vm.aStatus='';
        /*vm.additionalFile=additionalFile;*/
        vm.addFileName='';
        vm.summaryData='';
      //  vm.submiTable=submitTable;


         console.log("file---->",vm.opportunityMaster);

        var unsubscribe = $rootScope.$on('researchRepositoryLearningSystemApp:opportunityMasterUpdate', function(event, result) {
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
        $scope.showdocument = function()
        {
            $scope.myvar = !$scope.myvar;
           /* $scope.addFile = !$scope.addFile;*/

        };

        $scope.fileformat = ["Presentation", "Excel Model", "Quarterly Updates","Miscellaneous"];

        $scope.getselectval = function () {
           console.log('Name: ' + $scope.selitem);
        };






        $scope.submitTable = function() {

           console.log("DATA----->",vm.opportunityMaster.financialSummaryData);

           console.log("DATA----->",vm.opportunityMaster.nonFinancialSummaryData);

            //console.log("bdsfjhj",tableValue);

            OpportunityMaster.summarydatavalues(vm.opportunityMaster, function (resp) {
                console.log("safsdaf---->"+resp);
            }, function (err) {
                console.log(err);
            });

        }

        $scope.ok = function(){

            $scope.isDisabled = false;

        };

        $scope.cancel = function(){

            $scope.isDisabled = true;

        };

        $scope.updateSum = function() {
            $scope.sumVal = ($scope.vm.opportunityMaster.financialSummaryData.netIntOne * 1) + ($scope.vm.opportunityMaster.financialSummaryData.nonIntOne * 1);
          }

        $scope.getTotal = function(val1, val2, val3) {

            var	result = parseFloat(val1) + parseFloat(val2);
            result=(isNaN(result)) ? '':result;
            console.log("val3 ",val3);
            //console.log(vm.opportunityMaster.financialSummaryData);
            switch(val3){
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
            $scope.getFinPbv = function(val1, val2, val3) {

            	var result = (parseFloat(val1) / parseFloat(val2)).toFixed(2);
            	result=(isNaN(result)) ? '':result;
            	 switch(val3){
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
          	return result;
            };
            $scope.getFinRoe = function(val1, val2,val3,val4) {
          	  if(val2==0)
              	 {	var result = parseFloat(val1) / parseFloat(val3);
               	result=(result*100).toFixed(2);  }
             	 else{
              		var result = parseFloat(val1) / ((parseFloat(val2)+parseFloat(val3))/2);
               	result=(result*100).toFixed(2);
             	 }
          		result=(isNaN(result)) ? '':result;
          		 switch(val4){
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
              	return result;
              };
          	$scope.getNonGrowth = function(val1, val2, val3) {

    			var result = (parseFloat(val2) / parseFloat(val1)) - 1;
    			result = (result * 100).toFixed(2);
    			result = (isNaN(result)) ? '' : result;
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
    			return result;
    		};
    		$scope.getNonMargin = function(val1, val2, val3) {

    			var result = parseFloat(val1) / parseFloat(val2);
    			result = (result * 100).toFixed(2);
    			result = (isNaN(result)) ? '' : result;
    			switch (val3) {
    			case 1:
    				vm.opportunityMaster.nonFinancialSummaryData.marginOne = result;
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
    			return result;
    		};

    		$scope.getNonPbt = function(val1, val2, val3, val4, val5) {

    			var result = parseFloat(val1) + parseFloat(val2) - parseFloat(val3)
    					- parseFloat(val4);
    			result = (isNaN(result)) ? '' : result;
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
    			return result;
    		};

    		$scope.getNonPat = function(val1, val2, val3) {

    			var result = parseFloat(val1) - parseFloat(val2);
    			result = (isNaN(result)) ? '' : result;
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
    			return result;
    		};
    		$scope.getNonPe = function(val1, val2, val3) {

    			var result = (parseFloat(val1) / parseFloat(val2)).toFixed(2);
    			result = (isNaN(result)) ? '' : result;
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
    			return result;
    		};
    		$scope.getNonRoe = function(val1, val2, val3) {

    			if (val2 == 0) {
    				var result = parseFloat(val1) / parseFloat(val3);
    				result = (result * 100).toFixed(2);
    			} else {
    				var result = parseFloat(val1)
    						/ ((parseFloat(val2) + parseFloat(val3)) / 2);
    				result = (result * 100).toFixed(2);
    			}
    			result = (isNaN(result)) ? '' : result;
    			switch (val3) {
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
    			return result;
    		};
        $scope.isDisabled = true;

        $scope.open = function (status) {

            var modalInstance = $uibModal.open({
                templateUrl: 'app/entities/opportunity-master/opportunity-description-dialog.html',
                controllerAs: '$ctrl',
                controller: function() {
                    this.message = 'some message';

                    this.ok = function() {
                        console.log(this.description);
                        console.log(vm.opportunityMaster.id);

                        var val = this.description;

                        OpportunityMaster.description({statusDes : val,id: vm.opportunityMaster.id,oppStatus:status}, function(resp){
                            console.log(resp);
                            vm.opportunityMaster.oppStatus = resp.oppStatus;
                        }, function(err) {
                            console.log(err);
                        });
                        var inputData = {};

                        inputData.opportunityComments = val +" - "+status;
                        inputData.opportunityMaster=vm.opportunityMaster;

                        FileUploadComments.save(inputData, function(resp) {
                            vm.opportunityMaster.fileUploadCommentList.push(resp);},
                            function(err) {
                                console.log(err);
                            });
                        modalInstance.close();
                    };

                    this.cancel = function() {
                        modalInstance.dismiss('cancel');
                    };
                },
                resolve: {

                }
            });


        };

    /*    function submitTable()
        {


          console.log(summaryData.bWeight);


        }*/

        function load() {

            /*vm.opportunityMaster.fileUploadCommentList = OpportunityMaster.get({id : $stateParams.id}, function(resp){
             console.log(resp);*/
            vm.opportunityMaster.fileUploadCommentList;
            vm.opportunityMaster.status;
            console.log(vm.opportunityMaster.fileUploadCommentList)
              /* }, function(err) {
                console.log(err);
            });*/
        }

        function clear () {
        	vm.newdoc = "";
        	$scope.myvar = false;
        	vm.fileName="";
        	/*vm.addNewdoc="";
        	$scope.addFile = false;
        	vm.htmlContent="";*/
        }

        function upload () {
        console.log('uploading....');
        console.log($scope.selitem);
        console.log(vm.uploadfileName);
        var selectitem = $scope.selitem;
            vm.isSaving = true;

                //OpportunityMaster.saveWithUpload({inputData: vm.opportunityMaster, fileUpload: vm.opportunityMaster.fileUpload}, onSaveSuccess, onSaveError);
                Upload.upload({
                    url: 'api/file-uploads',
                    data: {fileUploads: vm.opportunityMaster.fileUpload},
                    params: {oppId: vm.opportunityMaster.id,filetype:selectitem,uploadfileName:vm.uploadfileName}// {oppCode: inputData.oppCode, oppName: inputData.oppName, oppDescription: inputData.oppDescription, strategyMasterId: inputData.strategyMasterId.id}
                }).then(function (resp) {
                    console.log(resp);
                    if(resp.status == 201) {
                    	vm.opportunityMaster.fileUploads.push(resp.data);
                    }
                }, function (resp) {
                    console.log(resp);
                }, function (evt) {
                    console.log(evt);
                    //var progressPercentage = parseInt(100.0 * evt.loaded / evt.total);
                    //console.log('progress: ' + progressPercentage + '% ' + evt.config.data.file.name);
                });
        }
        function selectFile (file) {
            console.log("File",file);
            vm.opportunityMaster.fileUpload = file;
        }

        function createFile()
        {
        	console.log(vm.newdoc);
        	console.log('File name......'+vm.fileName);
        	var doc = "<body>"+vm.newdoc+"</body>";
        	OpportunityMaster.wordCreation({fileContent: doc,fileName:vm.fileName,oppId:vm.opportunityMaster.id,
        		oppName:vm.opportunityMaster.masterName.oppName,oppId:vm.opportunityMaster.id}, function(result){
        			console.log('result',result);

        			vm.opportunityMaster.fileUploads.push(result);
        			console.log('opp',vm.opportunityMaster.fileUploads);

        			console.log("filename",result.fileName);
        			clear();
        		}, onSaveError);


        }


       /* function additionalFile()
        {
        	console.log(vm.addNewdoc);
        	var doc = "<body>"+vm.addNewdoc+"</body>";
        	console.log(vm.fileId);
        	OpportunityMaster.addWordCreation({fileContent: doc,fileId:vm.fileId}, function(result){
        			console.log('result',result);
        			vm.opportunityMaster.fileUploads.push(result);
        			//console.log('opp',vm.opportunityMaster.fileUploads);
        			clear();
        		}, onSaveError);


        }   */



        function saveComment() {
            vm.isSaving = true;
            var inputData = {};
            /*angular.forEach(vm.opportunityMaster.fileUploadCommentList, function(oppKey, oppValue){
            	if(vm.fileId == vm.opportunityMaster.fileUploads[oppValue].id){
            		inputData.fileUploadId = vm.opportunityMaster.fileUploads[oppValue];
            	}
            });*/

            //inputData.fileUploadId = vm.fileId;
            console.log( vm.fileComments, vm.opportunityMaster)
            inputData.opportunityComments = vm.fileComments;
            inputData.opportunityMaster=vm.opportunityMaster;

          FileUploadComments.save(inputData, function(resp) {
        	  vm.opportunityMaster.fileUploadCommentList.push(resp);
        	  vm.fileComments = "";
          }, onSaveError);
           // console.log(result);
           // vm.opportunityMaster.fileUploadCommentList = result;
        }
      /*  $scope.data='';*/
       function approveFile(status){
        	console.log(vm.opportunityMaster.id);

        	console.log('status..', status);

        	vm.opportunityMaster.oppStatus = status;

        	OpportunityMaster.update(vm.opportunityMaster, function(resp) {
        		//console.log(resp);
        	}, function(err) {
        		//console.log(err);
        	});

        }

        function loadFileContent(fileID) {
        	console.log(fileID);

        	vm.fileId = fileID;
            vm.fileUpload=fileID;

        /*	vm.fileId=fileID;

        	OpportunityMaster.getFileContent({id : fileID}, function(resp){
                console.log(resp);
               /!* console.log("my variable.. "+$scope.myvar);*!/
                vm.htmlContent = resp.htmlContent;
               // vm.fileUploadCommentList = resp.fileUploadCommentList;

                $scope.myvar = false;
                  }, function(err) {
                   console.log(err);
               });*/


            OpportunityMaster.downloadfilecontent(function(resp){
                console.log(resp);

            }, function(err) {
                console.log(err);
            });
        }


        function onSaveSuccess (result) {
            vm.isSaving = false;
            vm.opportunityMaster.fileUploadCommentList.push(result);
            vm.fileComments = "";
            //vm.load();
        }

        function onSaveError () {
            vm.isSaving = false;
        }
    }

})();
