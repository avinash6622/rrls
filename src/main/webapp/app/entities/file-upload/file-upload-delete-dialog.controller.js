(function() {
    'use strict';

    angular
        .module('researchRepositoryLearningSystemApp')
        .controller('FileUploadDeleteController',FileUploadDeleteController);

    FileUploadDeleteController.$inject = ['$uibModalInstance', 'entity', 'FileUpload'];

    function FileUploadDeleteController($uibModalInstance, entity, FileUpload) {
        var vm = this;

        vm.fileUpload = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            FileUpload.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
