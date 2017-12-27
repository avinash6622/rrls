(function() {
    'use strict';

    angular
        .module('researchRepositoryLearningSystemApp')
        .controller('RoleMasterDeleteController',RoleMasterDeleteController);

    RoleMasterDeleteController.$inject = ['$uibModalInstance', 'entity', 'RoleMaster'];

    function RoleMasterDeleteController($uibModalInstance, entity, RoleMaster) {
        var vm = this;

        vm.roleMaster = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            RoleMaster.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
