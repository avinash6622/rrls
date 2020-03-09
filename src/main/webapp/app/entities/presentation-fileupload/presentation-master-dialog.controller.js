(function() {
  "use strict";

  angular
    .module("researchRepositoryLearningSystemApp")
    .controller(
      "PresentationMasterDialogController",
      PresentationMasterDialogController
    );

  PresentationMasterDialogController.$inject = [
    "$timeout",
    "$scope",
    "$stateParams",
    "$uibModalInstance",
    "entity",
    "PresentationMaster"
  ];

  function PresentationMasterDialogController(
    $timeout,
    $scope,
    $stateParams,
    $uibModalInstance,
    entity,
    PresentationMaster
  ) {
    var vm = this;

    vm.presentationMaster = entity;
    vm.clear = clear;
    vm.datePickerOpenStatus = {};
    vm.openCalendar = openCalendar;
    vm.save = save;

    $timeout(function() {
      angular.element(".form-group:eq(1)>input").focus();
    });

    function clear() {
      $uibModalInstance.dismiss("cancel");
    }

    function save() {
      vm.isSaving = true;
      if (vm.presentationMaster.id !== null) {
        PresentationMaster.update(
          vm.presentationMaster,
          onSaveSuccess,
          onSaveError
        );
      } else {
        PresentationMaster.save(
          vm.presentationMaster,
          onSaveSuccess,
          onSaveError
        );
      }
    }

    function onSaveSuccess(result) {
      $scope.$emit(
        "researchRepositoryLearningSystemApp:presentationMasterUpdate",
        result
      );
      $uibModalInstance.close(result);
      vm.isSaving = false;
    }

    function onSaveError() {
      vm.isSaving = false;
    }

    vm.datePickerOpenStatus.dateActive = false;
    vm.datePickerOpenStatus.createdDate = false;
    vm.datePickerOpenStatus.updatedDate = false;

    function openCalendar(date) {
      vm.datePickerOpenStatus[date] = true;
    }
  }
})();
