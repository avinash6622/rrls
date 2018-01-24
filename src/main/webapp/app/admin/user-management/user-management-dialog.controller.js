<<<<<<< HEAD
(function() {
    'use strict';

    angular
        .module('researchRepositoryLearningSystemApp')
        .controller('UserManagementDialogController',UserManagementDialogController);

    UserManagementDialogController.$inject = ['$stateParams', '$uibModalInstance', 'entity', 'User','RoleMaster'];

    function UserManagementDialogController ($stateParams, $uibModalInstance, entity, User,RoleMaster) {
        var vm = this;

        vm.authorities = ['ROLE_ADMIN','ROLE_RA','ROLE_CIO','ROLE_DEALER','ROLE_SALE'];
        vm.roleMasters = RoleMaster.query();
        vm.users=User.query();
        vm.clear = clear;
        vm.languages = null;
        vm.save = save;
        vm.user = entity;



        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function onSaveSuccess (result) {
            vm.isSaving = false;
            $uibModalInstance.close(result);
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        function save () {
            vm.isSaving = true;
            if (vm.user.id !== null) {
                User.update(vm.user, onSaveSuccess, onSaveError);
            } else {
                vm.user.langKey = 'en';
                User.save(vm.user, onSaveSuccess, onSaveError);
            }
        }




    }
})();
=======
(function() {
    'use strict';

    angular
        .module('researchRepositoryLearningSystemApp')
        .controller('UserManagementDialogController',UserManagementDialogController);

    UserManagementDialogController.$inject = ['$stateParams', '$uibModalInstance', 'entity', 'User','RoleMaster'];

    function UserManagementDialogController ($stateParams, $uibModalInstance, entity, User,RoleMaster) {
        var vm = this;

        vm.authorities = ['ROLE_ADMIN','ROLE_RA','ROLE_CIO','ROLE_DEALER','ROLE_SALE'];
        vm.roleMasters = RoleMaster.query();
        vm.users=User.query();
        vm.clear = clear;
        vm.languages = null;
        vm.save = save;
        vm.user = entity;



        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function onSaveSuccess (result) {
            vm.isSaving = false;
            $uibModalInstance.close(result);
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        function save () {
            vm.isSaving = true;
            if (vm.user.id !== null) {
                User.update(vm.user, onSaveSuccess, onSaveError);
            } else {
                vm.user.langKey = 'en';
                User.save(vm.user, onSaveSuccess, onSaveError);
            }
        }




    }
})();
>>>>>>> branch 'v1.0-dev' of https://Girija_P@bitbucket.org/unifi-dev/research-repository-learning-system.git
