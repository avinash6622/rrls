(function() {
  "use strict";

  angular
    .module("researchRepositoryLearningSystemApp")
    .controller(
      "PresentationBrochureMasterUploadController",
      PresentationBrochureMasterUploadController
    );

  PresentationBrochureMasterUploadController.$inject = [
    "OpportunityMaster",
    "Principal",
    "$state",
    "ConfidentialLetters",
    "$scope",
    "$filter",
    "$http",
    "$sce",
    "Upload"
  ];

  function PresentationBrochureMasterUploadController(
    OpportunityMaster,
    Principal,
    $state,
    ConfidentialLetters,
    $scope,
    $filter,
    $http,
    $sce,
    Upload
  ) {
    var vm = this;

    vm.opportunityMasters = [];
    // vm.oppMasterId = "";
    vm.filetypeValue = "";
    vm.uploadfileNameValue = "";
    vm.upload = upload;
    vm.clear = clear;
    vm.selectFile = selectFile;
    vm.fileUpload = "";
    vm.loadAll = loadAll;
    vm.presentationMasterUpload;
    vm.fileTypeName = "";

    vm.loadAll();

    function loadAll() {
      $scope.$on("authenticationSuccess", function() {
        getAccount();
      });
    }

    getAccount();

    function getAccount() {
      Principal.identity().then(function(account) {
        vm.account = account;
        vm.isAuthenticated = Principal.isAuthenticated;
      });
    }

    var myDate = new Date();

    $scope.currentYear = $filter("date")(myDate, "yyyy");

    function upload() {
      var selectitem = $scope.selitem;
      vm.isSaving = true;

      console.log(
        "vm.filetypeValue___",
        vm.fileTypeName,
        "uploadfileNameValue___",
        vm.uploadfileNameValue,
        "+++",
        vm.fileDescriptionValue,
        vm.fileUpload.name
      );
      Upload.upload({
        // url: "api/confidenctial-letters",
        url: "api/brochure/mainFileUploads",
        data: { fileUploads: vm.fileUpload },
        params: {
          filetype: vm.fileTypeName,
          uploadfileName: vm.uploadfileNameValue,
          Strategy: $state.params.id,
          fileDescription: vm.fileDescriptionValue
        } // {oppCode: inputData.oppCode, oppName: inputData.oppName, oppDescription: inputData.oppDescription, strategyMasterId: inputData.strategyMasterId.id}
      }).then(
        function(resp) {
          if (resp.status == 201) {
          }
        },
        function(resp) {
          console.log("resp", resp);
        },
        function(evt) {
          console.log("evt", evt);
        }
      );
    }

    function selectFile(file) {
      console.log(file);
      var fileName = file.name;
      console.log(
        "ValueSlice",
        fileName.slice(fileName.indexOf(".") + 1, fileName.length + 1),
        fileName.slice(0, fileName.indexOf("."))
      );
      vm.fileTypeName = fileName.slice(
        fileName.indexOf(".") + 1,
        fileName.length + 1
      );
      vm.uploadfileNameValue = fileName.slice(0, fileName.indexOf("."));
      // vm.fileTypeName = fileName.slice(
      //   fileName.indexOf(".") + 1,
      //   fileName.length + 1
      // );
      vm.fileUpload = file;
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
  }
})();
