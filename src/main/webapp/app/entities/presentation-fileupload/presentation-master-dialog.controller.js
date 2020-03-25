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
    "$http",
    //"entity",
    "Upload",
    "PresentationMaster",
    "previousState"
  ];

  function PresentationMasterDialogController(
    $timeout,
    $scope,
    $stateParams,
    $http,
    // $uibModalInstance,
    //entity,
    Upload,
    PresentationMaster,
    previousState
  ) {
    var vm = this;

    console.log("scope", $scope);
    console.log("stateParams", $stateParams);
    vm.previousState = previousState.name;
    //vm.presentationMaster = entity;
    //vm.clear = clear;
    vm.datePickerOpenStatus = {};
    vm.openCalendar = openCalendar;
    // vm.save = save;
    vm.selectFile = selectFile;
    vm.upload = upload;
    vm.presentationId = "";
    vm.fileUploadSction = "";
    getDataFromId();

    $timeout(function() {
      angular.element(".form-group:eq(1)>input").focus();
    });

    vm.datePickerOpenStatus.dateActive = false;
    vm.datePickerOpenStatus.createdDate = false;
    vm.datePickerOpenStatus.updatedDate = false;

    function openCalendar(date) {
      vm.datePickerOpenStatus[date] = true;
    }

    function getDataFromId() {
      console.log("getDataFromId");
      return $http
        .get("/api/presentationList/getById?id=" + $stateParams.pId)
        .then(function(response) {
          console.log("Get Api response", response);
          // loadAll();
          var _data = response;
          var filepathStartIndex = _data.data.filePath;

          console.log(
            _data.data.filePath,
            "fileName",
            _data.data.fileName + "." + _data.data.fileContentType
          );
          // vm.fileUpload.name =
          //   _data.data.fileName + "." + _data.data.fileContentType;
          vm.fileUploadnamevalue =
            _data.data.presentationFileUpload.fileName +
            "." +
            _data.data.presentationFileUpload.fileContentType;
          vm.fileDescriptionValue = _data.data.presentationFileUpload.fileDesc;
          vm.uploadfileNameValue = _data.data.presentationFileUpload.fileName;
          vm.fileTypeName = _data.data.presentationFileUpload.fileContentType;
          vm.presentationId = _data.data.presentationFileUpload.id;
        })
        .catch(function(error) {
          console.log(error);
        });
    }

    function upload() {
      var selectitem = $scope.selitem;
      vm.isSaving = true;

      Upload.upload({
        // url: "api/confidenctial-letters",
        url: "/api/presentation/update/fileUploads",
        method: "PUT",
        data: { fileUploads: vm.fileUploadSction },
        params: {
          filetype: vm.fileTypeName,
          uploadfileName: vm.uploadfileNameValue,
          Strategy: $stateParams.id,
          fileDescription: vm.fileDescriptionValue,
          id: vm.presentationId
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

    // PUT /api/presentation/Update

    function selectFile(file) {
      vm.fileUploadSction = file;
      console.log("file", file);
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
      vm.fileUploadnamevalue = file.name;
    }
  }
})();
