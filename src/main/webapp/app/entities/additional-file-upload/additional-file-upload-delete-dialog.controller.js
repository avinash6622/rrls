(function() {
    'use strict';

    angular
        .module('researchRepositoryLearningSystemApp')
        .controller('AdditionalFileUploadDeleteController',AdditionalFileUploadDeleteController);

    AdditionalFileUploadDeleteController.$inject = ['$uibModalInstance', 'entity', 'AdditionalFileUpload'];

    function AdditionalFileUploadDeleteController($uibModalInstance, entity, AdditionalFileUpload) {
        var vm = this;

        vm.additionalFileUpload = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            AdditionalFileUpload.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
