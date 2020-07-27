(function() {
  "use strict";

  angular
    .module("researchRepositoryLearningSystemApp")
    .controller(
      "PresentationMasterDetailController",PresentationMasterDetailController);

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
    "$http",
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
    $http,
    $timeout,
    PresentationMaster
  ) {
    var vm = this;
    vm.strategyId = $scope.toStateParams.id;
    // vm.strategyMaster = entity;
    vm.previousState = previousState.name;
    vm.itemsPerPage = paginationConstants.itemsPerPage;
    vm.predicate = pagingParams.predicate;
    vm.reverse = true;
    vm.page = 1;
    vm.clear = clear;
    var data = new Date("2020-03-06T09:30:47Z");
    console.log("date", data.toLocaleDateString());

    console.log("valueeee", $scope.toStateParams.id);
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
    vm.PresentationFlag = true;
    vm.BrochureFlag = false;
    vm.predicate = "id";
    vm.reset = reset;
    vm.reverse = true;
    vm.getdate = "";
    vm.reverse = pagingParams.ascending;
    vm.predicate = pagingParams.predicate;
    vm.transition = transition;
    vm.presentationvalues = [];
    vm.loader = false;
    vm.fixedCollapse = fixedCollapse;
    vm.downlaodFile = downlaodFile;
    vm.downloadPresentation = downloadPresentation;
    vm.getBrochureData = getBrochureData;
    vm.getBrochureDataValues = [];
    vm.getBrochureDataValuesSupportFile = [];
    vm.presentationFunRun = presentationFun;
    vm.brochureFunRun = brochureFun;
    vm.brochureFileUploadSupportData = {};
    vm.getProperdate = getProperdateValue;
    // vm.PresentationFlag = PresentationFlag;
    // vm.BrochureFlag = BrochureFlag;
    loadAll();

    setTimeout(function() {
      getDecimalConfig();
    }, 3000);

    function getDecimalConfig() {}

    function loadAll() {
      console.log("urlValue", $state.params);
      console.log("eqweqeqwqewe");
      getBrochureData();
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


    function clear() {
      $state.go(
        $state.current.parent,
        {},
        {
          reload: true
        }
      );
    }

    function fixedCollapse(index, state, val) {
      console.log("Value in click");
      vm.getBrochureDataValues.forEach(function(value, key) {
        if (key === index) {
          value.expanded = state;
        } else {
          value.expanded = false;
        }
      });
      vm.edit = true;
    }

    function downloadPresentation(fileName) {
      console.log(typeof fileName);
      //var url = "/download/presentationAndBorchure?fileID="+fileName+"&flag="+"P";
      //window.open(url, "_blank");

      var data = fileName;
      data = data.replace(/\\/g, "/");
      console.log('After slash replace - ' + data);

      let urlValues = data.split('/');
      for (var i = 0; i < urlValues.length; i++) {
          if (urlValues[i].endsWith('.')) {
              urlValues[i] = urlValues[i].slice(0, -1);
          }
      }
      console.log(urlValues);
      data = urlValues.join('/');
      console.log('After dot replace - ' + data);
      var url = window.location.origin + data.split('webapp')[1];
      console.log("url -" + url);
      window.open(url, '_blank');
    }


    function downlaodFile(_data1,_data2) {
      console.log("downlaodFile");
      console.log("_data1", _data1, "_data2", _data2);
      var data = {
        fileID: _data2,
        flag: "P"
      };
      return $http
        .post("/download/presentationAndBorchure?fileID="+_data2+"&flag="+"P")
        .then(function(response) {
          console.log("download Api response", response);
          //var FileSaver = require('file-saver');
          //var blob = new Blob(response, {type: "text/plain;charset=utf-8"});
          //FileSaver.saveAs(response, "download.pdf");
        })
        .catch(function(error) {
          console.log(error);
        });
    }
    // vm.getExactData = getExactData;

    // function getExactData() {
    //   return new Date("2015-03-25T12:00:00Z");
    // }

    function presentationFun() {
      console.log("presentationFun");
      vm.BrochureFlag = false;
      vm.PresentationFlag = true;
    }

    function getProperdateValue() {
      console.log("Heloooooooooooooooooooooooooooooooooooooooo");
    }
    // function getProperdateValue(_data) {
    //   console.log("varen");
    //   var data = new Date(_data);
    //   // vm.getdate = data.toLocaleDateString();
    //   return data.toLocaleDateString();
    //   //console.log("getdate", vm.getdate);
    // }
    function brochureFun() {
      console.log("brochureFun");
      vm.PresentationFlag = false;
      vm.BrochureFlag = true;
    }

    // var app = angular.module("myApp", []);

    function getBrochureData() {
      // var brochureFileUploadSupportData = [];
      console.log("data");
      return $http
        .get(
          "/api/brochureMainFileList/viewByStrategy?strategyId=" +
            $scope.toStateParams.id
        )
        .then(function(response) {
          //var data = [];
          console.log("response in getBrochureData");
          console.log("getBrochureDataList", response);
          vm.getBrochureDataValues = response.data;
          vm.getBrochureDataValues.forEach((data, index) => {
            console.log(
              "123",
              data.brochureFileUpload.brochureSupportingFiles.length,
              "index" + index
            );

            if (data.brochureFileUpload.brochureSupportingFiles.length == 0) {
              vm.brochureFileUploadSupportData[index] = {};
              console.log(
                "vm.brochureFileUploadSupportData",
                vm.brochureFileUploadSupportData,
                "lenght",
                vm.brochureFileUploadSupportData.length
              );
            } else if (
              data.brochureFileUpload.brochureSupportingFiles.length != 0
            ) {
              vm.brochureFileUploadSupportData[index] = {};
              data.brochureFileUpload.brochureSupportingFiles.forEach(
                (data, ident) => {
                  console.log("data", data);
                  vm.brochureFileUploadSupportData[index] = data;
                  console.log("vm.brochure", vm.brochureFileUploadSupportData);
                }
              );
            }
          });
          console.log("getBrochureDataValues", vm.getBrochureDataValues);
          // loadAll();
        })
        .catch(function(error) {
          console.log(error);
        });
    }

    var acc = document.getElementsByClassName("accordion");
    var i;

    for (i = 0; i < acc.length; i++) {
      console.log("acc", acc, "i", i);
      acc[i].addEventListener("click", function() {
        this.classList.toggle("active");
        var panel = this.nextElementSibling;
        if (panel.style.maxHeight) {
          panel.style.maxHeight = null;
        } else {
          panel.style.maxHeight = panel.scrollHeight + "px";
        }
      });
    }

    $scope.deleteConfirm = function(_data) {
      console.log("am calling", _data, "$scope.params.id");
      var txt;
      if (confirm("Do you want to delete it?")) {
        txt = "You pressed OK!";
        // console.log("confidential letter");
        //console.log(confidentialLetter);
        return $http
          .delete(
            "/api/presentationFile/delete?strategyId=" +
              $scope.toStateParams.id +
              "&presentationId=" +
              _data
          )
          .then(function(response) {
            console.log("response in delete");
            console.log(response);
            loadAll();
          })
          .catch(function(error) {
            console.log(error);
          });
      } else {
        txt = "You pressed Cancel!";
      }
      // document.getElementById("demo").innerHTML = txt;
    };

    $scope.deleteBrochureConfirm = function(_data) {
      console.log("am calling brochure", _data, "$scope.params.id");
      var txt;
      if (confirm("Do you want to delete it?")) {
        txt = "You pressed OK!";
        // console.log("confidential letter");
        //console.log(confidentialLetter);
        return $http
          .delete(
            "/api/brochureMainFile/delete?strategyId=" +
              $scope.toStateParams.id +
              "&borchureId=" +
              _data
          )
          .then(function(response) {
            console.log("response in delete");
            console.log(response);
            loadAll();
          })
          .catch(function(error) {
            console.log(error);
          });
      } else {
        txt = "You pressed Cancel!";
      }
      // document.getElementById("demo").innerHTML = txt;
    };


    $scope.deleteSupportingBrochureConfirm = function(_data) {
      console.log("am calling brochuresuppoting", _data, "$scope.params.id");
      var txt;
      if (confirm("Do you want to delete it?")) {
        txt = "You pressed OK!";
        // console.log("confidential letter");
        //console.log(confidentialLetter);
        return $http
          .delete("/api/brochureSupportingFile/delete?borchureSupportingId="+_data)
          .then(function(response) {
            console.log("response in delete");
            console.log(response);
            loadAll();
          })
          .catch(function(error) {
            console.log(error);
          });
      } else {
        txt = "You pressed Cancel!";
      }
      // document.getElementById("demo").innerHTML = txt;
    };

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
