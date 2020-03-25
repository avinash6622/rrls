(function() {
  "use strict";

  angular
    .module("researchRepositoryLearningSystemApp")
    .controller(
      "PresentationBrochureSupportFileUpdateDialogController",
      PresentationBrochureSupportFileUpdateDialogController
    );

  PresentationBrochureSupportFileUpdateDialogController.$inject = [
    "$timeout",
    "$scope",
    "$stateParams",
    "$http",
    //"entity",
    "Upload",
    "PresentationMaster"
  ];

  function PresentationBrochureSupportFileUpdateDialogController(
    $timeout,
    $scope,
    $stateParams,
    $http,
    Upload,
    PresentationMaster
  ) {
    var vm = this;

    console.log("scope", $scope);
    console.log("stateParams", $stateParams);
    vm.datePickerOpenStatus = {};
    vm.openCalendar = openCalendar;
    vm.selectFile = selectFile;
    vm.upload = upload;
    vm.brochureId = "";
    vm.brochureSupportingFiles = {};
    vm.fileUploadSction = "";
    vm.supportId = "";
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
      getMainDataFromId();
      return $http
        .get("/api/brochureSupportedFileList/getById?id=" + $stateParams.bId)
        .then(function(response) {
          console.log("Get Api response", response);
          // loadAll();
          var _data = response;
          //vm.brochureSupportingFiles.push(response.data);
          vm.brochureSupportingFiles.id = _data.data.id;
          vm.brochureSupportingFiles.fileName = _data.data.fileName;
          vm.brochureSupportingFiles.filePath = _data.data.filePath;
          vm.brochureSupportingFiles.fileContentType =
            _data.data.fileContentType;
          vm.brochureSupportingFiles.fileStatus = _data.data.fileStatus;
          vm.brochureSupportingFiles.fileDesc = _data.data.fileDesc;
          vm.brochureSupportingFiles.createdBy = _data.data.createdBy;
          vm.brochureSupportingFiles.createdDate = _data.data.createdDate;
          vm.brochureSupportingFiles.lastmodifiedBy = _data.data.lastmodifiedBy;
          vm.brochureSupportingFiles.lastModifiedDate =
            _data.data.lastModifiedDate;
          console.log(
            "brochureSupportingFilessingleidcall",
            vm.brochureSupportingFiles
          );

          vm.supportId = _data.data.id;
          console.log(
            _data.data.filePath,
            "fileName",
            _data.data.fileName + "." + _data.data.fileContentType
          );

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

    function getMainDataFromId() {
      console.log("getDataFromId");
      return $http
        .get("/api/brochureMainFileList/getById?id=" + $stateParams.bSID)
        .then(function(response) {
          console.log("bSID Api response", response);
          // loadAll();
          var _data = response;
          vm.brochureFileUpload = {};
          vm.brochureFileUpload = response.data;
          vm.brochureSupportingFiles.brochureFileUpload = response.data;
          // var filepathStartIndex = _data.data.filePath;
          console.log("brochureFileUpload", vm.brochureFileUpload);
          console.log(
            "brochureSupportingFilesmaindata",
            vm.brochureSupportingFiles
          );
          // vm.fileUpload.name =
          //   _data.data.fileName + "." + _data.data.fileContentType;
        })
        .catch(function(error) {
          console.log(error);
        });
    }

    function upload() {
      console.log("upload");
      // vm.brochureFileUpload.fileDesc = vm.fileDescriptionValue;
      vm.brochureSupportingFiles.fileDesc = vm.fileDescriptionValue;
      console.log("vm.brochureSupportingFiles", vm.brochureSupportingFiles);
      return $http
        .put("/api/brochureSupportingFile/Update", vm.brochureSupportingFiles)
        .then(function(response) {
          console.log("vm.brochureFileUpload Api response", response);
        })
        .catch(function(error) {
          console.log(error);
        });
    }

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

// {
//   "brochureFileUpload": {
//     "brochureSupportingFiles": [
//       {}
//     ],
//     "createdBy": "string",
//     "createdDate": "2020-03-23T07:17:50.328Z",
//     "fileContentType": "string",
//     "fileDesc": "Priya",
//     "fileName": "string",
//     "filePath": "string",
//     "fileStatus": "string",
//     "id": 9,
//     "lastModifiedDate": "2020-03-23T07:17:50.328Z",
//     "lastmodifiedBy": "string"
//   },
//   "createdBy": "string",
//   "createdDate": "2020-03-23T07:17:50.328Z",
//   "fileContentType": "string",
//   "fileDesc": "data",
//   "fileName": "string",
//   "filePath": "string",
//   "fileStatus": "string",
//   "id": 16,
//   "lastModifiedDate": "2020-03-23T07:17:50.328Z",
//   "lastmodifiedBy": "string"
// }

// vm.brochureFileUploadbSID.brochureSupportingFiles.forEach(
//   (value, index) => {
//     if (value.id == vm.supportId) {
//       console.log(
//         "vm.fileDescriptionValuesuccessId",
//         vm.fileDescriptionValue
//       );
//       value.fileDesc = vm.fileDescriptionValue;
//     }
//   }
// );
// vm.brochureSupportingFiles = vm.brochureFileUploadbSID;
