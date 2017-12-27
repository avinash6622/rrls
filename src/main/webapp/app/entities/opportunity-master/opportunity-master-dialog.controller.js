(function() {
    'use strict';

    angular
        .module('researchRepositoryLearningSystemApp')
        .controller('OpportunityMasterDialogController', OpportunityMasterDialogController);

    OpportunityMasterDialogController.$inject = ['$timeout', '$scope', '$state', '$stateParams', 'entity', 'Upload', 'OpportunityMaster', 'StrategyMaster', 'OpportunityName'];

    function OpportunityMasterDialogController ($timeout, $scope, $state, $stateParams, entity, Upload, OpportunityMaster, StrategyMaster, OpportunityName) {
        var vm = this;

        vm.opportunityMaster = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.strategymasters = StrategyMaster.query();
        vm.opportunityNames = $scope.opportunityNames = OpportunityName.query();
        vm.upload = upload;
        vm.selectFile = selectFile;
       /* vm.saveDoc=saveDoc;*/
        vm.readOnly = false;

        // Editor options.
        $scope.options = {
            language: 'en',
            allowedContent: true,
            entities: false,
            entities_greek: false,
            entities_latin: false,
            entities_processNumerical: 'force',
            basicEntities: false,
            extraPlugins: 'htmlwriter'
        };

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $state.go($state.current.parent, {}, {reload: true});
        }

        function save () {
            vm.isSaving = true;
            if (vm.opportunityMaster.id !== null) {
                OpportunityMaster.update(vm.opportunityMaster, onSaveSuccess, onSaveError);
            } else {
                OpportunityMaster.save(vm.opportunityMaster, onSaveSuccess, onSaveError);
            }
        }
       /* function saveDoc () {
            vm.isSaving = true;            
                OpportunityMaster.save(vm.fileDocSave, onSaveSuccess, onSaveError);             
        }*/

        function onSaveSuccess (result) {
            $scope.$emit('researchRepositoryLearningSystemApp:opportunityMasterUpdate', result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        function upload () {
            vm.isSaving = true;
            if (vm.opportunityMaster.id !== null) {

            } else {
                var inputData = vm.opportunityMaster;
                console.log(inputData);
                inputData.strategyMasterId = vm.opportunityMaster.strategyMasterId.id;

                //OpportunityMaster.saveWithUpload({inputData: vm.opportunityMaster, fileUpload: vm.opportunityMaster.fileUpload}, onSaveSuccess, onSaveError);
                Upload.upload({
                    url: 'api/opportunity-masters',
                    data: {fileUpload: vm.opportunityMaster.fileUpload},
                    params: inputData// {oppCode: inputData.oppCode, oppName: inputData.oppName, oppDescription: inputData.oppDescription, strategyMasterId: inputData.strategyMasterId.id}
                }).then(function (resp) {
                    onSaveSuccess(resp);
                }, function (resp) {
                    onSaveError(resp);
                }, function (evt) {
                    //var progressPercentage = parseInt(100.0 * evt.loaded / evt.total);
                    //console.log('progress: ' + progressPercentage + '% ' + evt.config.data.file.name);
                });
            }
        }

        function selectFile (file) {
            vm.opportunityMaster.fileUpload = file;
        }

        vm.datePickerOpenStatus.createdDate = false;
        vm.datePickerOpenStatus.updatedDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }

        $scope.changeEditor = function () {
            //console.log(vm.opportunityMaster.htmlContent);
        };
    }
})();
