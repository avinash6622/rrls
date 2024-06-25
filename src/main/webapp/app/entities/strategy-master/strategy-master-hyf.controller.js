(function() {
    'use strict';

    angular
        .module('researchRepositoryLearningSystemApp')
        .controller('StrategyMasterHyfController', StrategyMasterHyfController);

    StrategyMasterHyfController.$inject = ['StrategyMasterHyf', 'CompanyService', 'ParseLinks', 'AlertService', 'paginationConstants', '$scope', '$filter', 'Upload', '$http'];

    function StrategyMasterHyfController(StrategyMasterHyf, CompanyService, ParseLinks, AlertService, paginationConstants, $scope, $filter, Upload, $http) {
        var vm = this;

        vm.strategyMastersHyf = [];
        vm.companyNames = [];
        vm.loadAllCompanies = loadAllCompanies;
        vm.fileUpload = "";
        vm.upload = upload;
        vm.addRow = addRow; // Added the addRow function
        vm.getHyfFileUpload = getHyfFileUpload;
        vm.itemsPerPage = paginationConstants.itemsPerPage;
        vm.page = 0;
        vm.links = { last: 0 };
        vm.predicate = 'id';
        vm.reverse = true;
        vm.save = save;
        vm.selectFile = selectFile;
        loadAll();
        loadAllCompanies();

        // Datepicker options
        vm.dateOptions = { formatYear: 'yyyy', startingDay: 1 };
        vm.datepickers = [];
        vm.openDatePicker = function($event, index) {
            $event.preventDefault();
            $event.stopPropagation();
            vm.datepickers[index] = true;
        };

        function loadAll() {
            StrategyMasterHyf.query({ page: vm.page, size: vm.itemsPerPage }, onSuccess, onError);

            function onSuccess(data, headers) {
                vm.links = ParseLinks.parse(headers('link'));
                vm.totalItems = headers('X-Total-Count');
                for (var i = 0; i < data.length; i++) {
                    vm.strategyMastersHyf.push(data[i]);
                }
            }

            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        function loadAllCompanies() {
            CompanyService.query(function(data) {
            console.log(data);
                vm.companyNames = data;
            });
        }

        function selectFile(file, strategyMaster) {
            strategyMaster.termSheetFileName = file.name;
            vm.fileUpload = file;
        }

        function upload() {
            Upload.upload({ url: "api/upload-hyf", data: { files: vm.fileUpload } }).then(
                function(resp) {
                    if (resp.status == 201) {
                    }
                },
                function(resp) {
                    console.log("resp", resp);
                },
                function(evt) {
                    console.log("evt", evt);
                }
            );
        }

        function getHyfFileUpload(file) {
            return $http.get('/api/hyf/termSheetDownload/' + file).then(
                function(response) {
                    window.location.href = response.config.url;
                }).catch(function(error) {
                    console.log(error);
                });
        }

        function save() {
            vm.isSaving = true;
            vm.strategyMastersHyf.forEach(function(strategyMaster) {
                strategyMaster.maturityDate = $filter('date')(strategyMaster.maturityDate, 'dd MMM yyyy');
            });

            StrategyMasterHyf.save(vm.strategyMastersHyf, onSaveSuccess, onSaveError)
                .then(function() {
                    loadAll();
                })
                .catch(function(error) {
                    console.error("Save error:", error);
                })
                .finally(function() {
                    vm.isSaving = false;
                });
        }

        function onSaveSuccess(result) {
            vm.isSaving = false;
        }

        function onSaveError() {
            vm.isSaving = false;
        }

        function addRow() {
            var newRow = { isin: '', companyName: '', maturityDate: '', termSheetFileName: '' };
            vm.strategyMastersHyf.push(newRow);
        }

        vm.addRowAfter = function(index) {
            var newRow = { isin: '', companyName: '', maturityDate: '', termSheetFileName: '' };
            vm.strategyMastersHyf.splice(index + 1, 0, newRow);
        };
    }
})();
