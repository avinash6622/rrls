(function() {
  "use strict";

  angular
    .module("researchRepositoryLearningSystemApp")
    .controller(
      "PresentationBrochureMasterDialogController",
      PresentationBrochureMasterDialogController
    );

  PresentationBrochureMasterDialogController.$inject = [
    "$timeout",
    "$scope",
    "$stateParams",
    "$http",
    //"entity",
    "Upload",
    "PresentationMaster",
    "previousState"
  ];

  function PresentationBrochureMasterDialogController(
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
    vm.brochureFileUpload = [];
    vm.openCalendar = openCalendar;
    // vm.save = save;
    vm.selectFile = selectFile;
    vm.upload = upload;
    vm.brochureId = "";
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
        .get("/api/brochureMainFileList/getById?id=" + $stateParams.bId)
        .then(function(response) {
          console.log("Get Api response", response);
          // loadAll();
          var _data = response;
          vm.brochureFileUpload = response.data;
          var filepathStartIndex = _data.data.filePath;
          console.log(
            "brochureFileUpload",
            vm.brochureFileUpload,
            "_data",
            _data
          );
          console.log(
            _data.data.filePath,
            "fileName",
            _data.data.fileName + "." + _data.data.fileContentType
          );
          // vm.fileUpload.name =
          //   _data.data.fileName + "." + _data.data.fileContentType;
          vm.fileUploadnamevalue =
            _data.data.fileName + "." + _data.data.fileContentType;
          vm.fileDescriptionValue = _data.data.fileDesc;
          vm.uploadfileNameValue = _data.data.fileName;
          vm.fileTypeName = _data.data.fileContentType;
          vm.brochureId = _data.data.id;
        })
        .catch(function(error) {
          console.log(error);
        });
    }

    function upload() {
      console.log("upload");
      vm.brochureFileUpload.fileDesc = vm.fileDescriptionValue;
      return $http
        .put("/api/brochureMainFile/Update", vm.brochureFileUpload)
        .then(function(response) {
          console.log("vm.brochureFileUpload Api response", response);
        })
        .catch(function(error) {
          console.log(error);
        });
    }

    // function upload() {
    //   var selectitem = $scope.selitem;
    //   vm.isSaving = true;

    //   vm.brochureFileUpload.fileDesc = vm.fileDescriptionValue;

    //   console.log("brochureFileUploaduploaddata", vm.brochureFileUpload);

    //   Upload.upload({
    //     // url: "api/confidenctial-letters",
    //     url: "/api/brochureMainFile/Update",
    //     method: "PUT",
    //     data: vm.brochureFileUpload

    //   }).then(
    //     function(resp) {
    //       if (resp.status == 201) {
    //       }
    //     },
    //     function(resp) {
    //       console.log("resp", resp);
    //     },
    //     function(evt) {
    //       console.log("evt", evt);
    //     }
    //   );
    // }

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
