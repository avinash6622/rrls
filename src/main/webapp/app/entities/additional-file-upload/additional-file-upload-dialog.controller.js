(function() {
    'use strict';

    angular
        .module('researchRepositoryLearningSystemApp')
        .controller('AdditionalFileUploadDialogController', AdditionalFileUploadDialogController);

    AdditionalFileUploadDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'AdditionalFileUpload', 'FileUpload'];

    function AdditionalFileUploadDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, AdditionalFileUpload, FileUpload) {
        var vm = this;

        vm.additionalFileUpload = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.fileuploads = FileUpload.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.additionalFileUpload.id !== null) {
                AdditionalFileUpload.update(vm.additionalFileUpload, onSaveSuccess, onSaveError);
            } else {
                AdditionalFileUpload.save(vm.additionalFileUpload, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('researchRepositoryLearningSystemApp:additionalFileUploadUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


        vm.setFileData = function ($file, additionalFileUpload) {
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        additionalFileUpload.fileData = base64Data;
                        additionalFileUpload.fileDataContentType = $file.type;
                    });
                });
            }
        };
        vm.datePickerOpenStatus.createdDate = false;
        vm.datePickerOpenStatus.updatedDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
