(function() {
    'use strict';

    angular
        .module('researchRepositoryLearningSystemApp')
        .controller('FileUploadCommentsDeleteController',FileUploadCommentsDeleteController);

    FileUploadCommentsDeleteController.$inject = ['$uibModalInstance', 'entity', 'FileUploadComments'];

    function FileUploadCommentsDeleteController($uibModalInstance, entity, FileUploadComments) {
        var vm = this;

        vm.fileUploadComments = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            FileUploadComments.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
