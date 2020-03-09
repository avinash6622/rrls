(function() {
  "use strict";

  angular
    .module("researchRepositoryLearningSystemApp")
    .controller("PresentationMasterController", PresentationMasterController);

  PresentationMasterController.$inject = [
    "StrategyMaster",
    "PresentationMaster",
    "ParseLinks",
    "AlertService",
    "paginationConstants",
    "$scope",
    "$filter"
  ];

  function PresentationMasterController(
    StrategyMaster,
    PresentationMaster,
    ParseLinks,
    AlertService,
    paginationConstants,
    $scope,
    $filter
  ) {
    var vm = this;

    vm.presentationMasters = [];
    vm.loadPage = loadPage;
    vm.itemsPerPage = paginationConstants.itemsPerPage;
    vm.page = 0;
    vm.links = {
      last: 0
    };
    vm.predicate = "id";
    vm.reset = reset;
    vm.reverse = true;

    loadAll();

    var myDate = new Date();

    $scope.currentYear = $filter("date")(myDate, "yyyy");

    function loadAll() {
      StrategyMaster.query(
        {
          page: vm.page,
          size: vm.itemsPerPage,
          sort: sort()
        },
        onSuccess,
        onError
      );
      function sort() {
        var result = [vm.predicate + "," + (vm.reverse ? "asc" : "desc")];
        if (vm.predicate !== "id") {
          result.push("id");
        }
        return result;
      }

      function onSuccess(data, headers) {
        vm.links = ParseLinks.parse(headers("link"));
        vm.totalItems = headers("X-Total-Count");
        for (var i = 0; i < data.length; i++) {
          console.log("presentationMasters", vm.presentationMasters);
          vm.presentationMasters.push(data[i]);
        }
      }

      function onError(error) {
        AlertService.error(error.data.message);
      }
    }

    $scope.getTotal = function() {
      var total = 0;
      angular.forEach(vm.presentationMasters, function(el) {
        if (el.aum != null) {
          total += parseFloat(el.aum);
        }
      });
      return total;
    };

    function reset() {
      vm.page = 0;
      vm.presentationMasters = [];
      loadAll();
    }

    function loadPage(page) {
      vm.page = page;
      loadAll();
    }
  }

  function PresentationDataGetter(communicationLetter) {
    console.log("communicationLetter");
    console.log(communicationLetter.id);
    return $http
      .get("/api/communication-letter/" + communicationLetter)
      .then(function(response) {
        console.log("response in delete");
        console.log(response);
      })
      .catch(function(error) {
        console.log(error);
      });
  }
})();
