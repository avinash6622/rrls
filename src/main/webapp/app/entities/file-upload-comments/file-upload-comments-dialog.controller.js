(function() {
    'use strict';

    angular
        .module('researchRepositoryLearningSystemApp')
        .controller('FileUploadCommentsDialogController', FileUploadCommentsDialogController);

    FileUploadCommentsDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'FileUploadComments', 'FileUpload', 'AdditionalFileUpload'];

    function FileUploadCommentsDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, FileUploadComments, FileUpload, AdditionalFileUpload) {
        var vm = this;

        vm.fileUploadComments = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.fileuploads = FileUpload.query();
        vm.additionalfileuploads = AdditionalFileUpload.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.fileUploadComments.id !== null) {
                FileUploadComments.update(vm.fileUploadComments, onSaveSuccess, onSaveError);
            } else {
                FileUploadComments.save(vm.fileUploadComments, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('researchRepositoryLearningSystemApp:fileUploadCommentsUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.createdDate = false;
        vm.datePickerOpenStatus.updatedDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
