(function() {
    'use strict';

    angular
        .module('researchRepositoryLearningSystemApp')
        .controller('OpportunityMasterDeleteController',OpportunityMasterDeleteController);

    OpportunityMasterDeleteController.$inject = ['$uibModalInstance', 'entity', 'OpportunityMaster'];

    function OpportunityMasterDeleteController($uibModalInstance, entity, OpportunityMaster) {
        var vm = this;

        vm.opportunityMaster = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            OpportunityMaster.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
