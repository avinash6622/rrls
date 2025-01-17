(function() {
  "use strict";

  angular
    .module("researchRepositoryLearningSystemApp")
    .controller(
      "UserManagementDialogController",
      UserManagementDialogController
    );

  UserManagementDialogController.$inject = [
    "$stateParams",
    "$uibModalInstance",
    "entity",
    "User",
    "RoleMaster"
  ];

  function UserManagementDialogController(
    $stateParams,
    $uibModalInstance,
    entity,
    User,
    RoleMaster
  ) {
    var vm = this;

    vm.authorities = [
      "Admin",
      "Research",
      "CIO",
      "Dealer",
      "Sale",
      "RM",
      "Master",
      "Communication_Letters",
      "Confidential_Letters",
      "Due_Diligence",
      "Presentation_Brochure"
    ];
    vm.roleMasters = RoleMaster.query();
    vm.users = User.query();
    vm.clear = clear;
    vm.languages = null;
    vm.save = save;
    vm.user = entity;

    function clear() {
      $uibModalInstance.dismiss("cancel");
    }

    function onSaveSuccess(result) {
      vm.isSaving = false;
      $uibModalInstance.close(result);
    }

    function onSaveError() {
      vm.isSaving = false;
    }

    function save() {
      vm.isSaving = true;
      if (vm.user.id !== null) {
        User.update(vm.user, onSaveSuccess, onSaveError);
      } else {
        vm.user.langKey = "en";
        User.save(vm.user, onSaveSuccess, onSaveError);
      }
    }
  }
})();
