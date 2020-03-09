(function() {
  "use strict";

  angular
    .module("researchRepositoryLearningSystemApp")
    .controller(
      "PresentationMasterDetailController",
      PresentationMasterDetailController
    );

  PresentationMasterDetailController.$inject = [
    "$scope",
    "$rootScope",
    "$stateParams",
    "previousState",
    "$filter",
    "pagingParams",
    "ParseLinks",
    "paginationConstants",
    "$state",
    "$timeout",
    "PresentationMaster"
  ];

  function PresentationMasterDetailController(
    $scope,
    $rootScope,
    $stateParams,
    previousState,
    $filter,
    pagingParams,
    ParseLinks,
    paginationConstants,
    $state,
    $timeout,
    PresentationMaster
  ) {
    var vm = this;

    // vm.strategyMaster = entity;
    vm.previousState = previousState.name;
    vm.itemsPerPage = paginationConstants.itemsPerPage;
    vm.predicate = pagingParams.predicate;
    vm.reverse = true;
    vm.page = 1;
    //  vm.opportunitySummaryData = vm.strategyMaster.opportunitySummaryData;

    vm.itemsValue = "Opportunities";

    /*     var unsubscribe = $rootScope.$on('researchRepositoryLearningSystemApp:strategyMasterUpdate', function(event, result) {
            vm.strategyMaster = result;

        });
        $scope.$on('$destroy', unsubscribe);*/

    vm.loadPage = loadPage;

    vm.itemsPerPage = paginationConstants.itemsPerPage;
    vm.page = 1;
    vm.oppmas = [];
    vm.totalItems = null;
    vm.links = {
      last: 0
    };
    vm.predicate = "id";
    vm.reset = reset;
    vm.reverse = true;
    vm.reverse = pagingParams.ascending;
    vm.predicate = pagingParams.predicate;
    vm.transition = transition;
    vm.presentationvalues = [];
    vm.loader = false;

    loadAll();

    setTimeout(function() {
      getDecimalConfig();
    }, 3000);

    function getDecimalConfig() {}

    function loadAll() {
      console.log($state.params);

      PresentationMaster.getPresentationDetail(
        {
          page: pagingParams.page - 1,
          size: vm.itemsPerPage,
          id: $stateParams.id
        },
        onSuccess,
        onError
      );
    }

    function onSuccess(data, headers) {
      vm.links = ParseLinks.parse(headers("link"));
      vm.totalItems = headers("X-Total-Count");
      vm.queryCount = vm.totalItems;
      vm.page = pagingParams.page;
      vm.presentationvalues = data;

      console.log("presentationvalues", vm.presentationvalues);

      vm.loader = true;

      $timeout(function() {
        console.log(vm.loader);
        $("#mytable1").CongelarFilaColumna({
          Columnas: 5,
          width: "100%",
          height: "100%"
        });

        $("#table-table").css({
          visibility: "visible"
        });

        vm.loader = false;
      }, 2000);
    }
    function onError(error) {
      AlertService.error(error.data.message);
    }

    function loadPage(page) {
      vm.page = page;
      loadAll();
    }

    function sort() {
      var result = [vm.predicate + "," + (vm.reverse ? "asc" : "desc")];
      if (vm.predicate !== "id") {
        result.push("id");
      }
      return result;
    }

    function reset() {
      vm.page = 0;
      vm.opportunityMasters = [];
      loadAll();
    }

    var myDate = new Date();

    var previousYear = new Date(myDate);

    previousYear.setYear(myDate.getFullYear() - 1);

    var previousYearBefore = new Date(previousYear);
    previousYearBefore.setYear(previousYear.getFullYear() - 1);

    var nextYear = new Date(myDate);

    nextYear.setYear(myDate.getFullYear() + 1);

    var nextYearNext = new Date(nextYear);

    nextYearNext.setYear(nextYear.getFullYear() + 1);

    $scope.currentYear = $filter("date")(myDate, "yyyy");

    $scope.nextYear = $filter("date")(nextYear, "yyyy");

    $scope.prevYear = $filter("date")(previousYear, "yyyy");

    $scope.prevYearBefore = $filter("date")(previousYearBefore, "yyyy");

    $scope.neYearNext = $filter("date")(nextYearNext, "yyyy");

    function transition() {
      $state.transitionTo($state.$current, {
        page: vm.page,
        sort: vm.predicate + "," + (vm.reverse ? "asc" : "desc"),
        search: vm.currentSearch,
        id: $stateParams.id
      });
    }
  }
})();
