(function() {
    'use strict';

    angular
        .module('researchRepositoryLearningSystemApp')
        .controller('RoleMasterDialogController', RoleMasterDialogController);

    RoleMasterDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'RoleMaster'];

    function RoleMasterDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, RoleMaster) {
        var vm = this;

        vm.roleMaster = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.roleMaster.id !== null) {
                RoleMaster.update(vm.roleMaster, onSaveSuccess, onSaveError);
            } else {
                RoleMaster.save(vm.roleMaster, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('researchRepositoryLearningSystemApp:roleMasterUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
