(function() {
    'use strict';

    angular
        .module('researchRepositoryLearningSystemApp')
        .controller('StrategyMasterDeleteController',StrategyMasterDeleteController);

    StrategyMasterDeleteController.$inject = ['$uibModalInstance', 'entity', 'StrategyMaster'];

    function StrategyMasterDeleteController($uibModalInstance, entity, StrategyMaster) {
        var vm = this;

        vm.strategyMaster = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            StrategyMaster.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
