(function() {
  "use strict";

  angular
    .module("researchRepositoryLearningSystemApp")
    .controller(
      "PresentationMasterDeleteController",
      PresentationMasterDeleteController
    );

  PresentationMasterDeleteController.$inject = [
    "$uibModalInstance",
    "entity",
    "PresentationMaster"
  ];

  function PresentationMasterDeleteController(
    $uibModalInstance,
    entity,
    PresentationMaster
  ) {
    var vm = this;

    vm.presentationMaster = entity;
    vm.clear = clear;
    vm.confirmDelete = confirmDelete;

    function clear() {
      $uibModalInstance.dismiss("cancel");
    }

    function confirmDelete(id) {
      PresentationMaster.delete({ id: id }, function() {
        $uibModalInstance.close(true);
      });
    }
  }
})();
