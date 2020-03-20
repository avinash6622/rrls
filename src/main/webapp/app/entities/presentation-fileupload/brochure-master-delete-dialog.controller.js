(function() {
  "use strict";

  angular
    .module("researchRepositoryLearningSystemApp")
    .controller(
      "BrochureMasterDeleteController",
      BrochureMasterDeleteController
    );

  BrochureMasterDeleteController.$inject = [
    "$uibModalInstance",
    "entity",
    "PresentationMaster"
  ];

  function BrochureMasterDeleteController(
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
