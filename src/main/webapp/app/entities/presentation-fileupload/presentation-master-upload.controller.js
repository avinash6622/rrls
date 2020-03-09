(function() {
  "use strict";

  angular
    .module("researchRepositoryLearningSystemApp")
    .controller(
      "PresentationMasterUploadController",
      PresentationMasterUploadController
    );

  PresentationMasterUploadController.$inject = [
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

  function PresentationMasterUploadController(
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
        vm.filetypeValue,
        vm.uploadfileNameValue,
        vm.fileDescriptionValue,
        vm.fileUpload
      );
      Upload.upload({
        // url: "api/confidenctial-letters",
        url: "api/presentation-file-uploads",
        data: { fileUploads: vm.fileUpload },
        params: {
          filetype: vm.filetypeValue,
          uploadfileName: vm.uploadfileNameValue,
          Strategy: 2,
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
