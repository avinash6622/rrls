(function() {
    'use strict';

    angular
        .module('researchRepositoryLearningSystemApp')
        .controller('PoliciesFileUploadDialogController', PoliciesFileUploadDialogController);

    PoliciesFileUploadDialogController.$inject = ['Principal', '$state', 'PoliciesFileUpload', '$scope', '$filter', '$http', '$sce', 'Upload'];

    function PoliciesFileUploadDialogController (Principal, $state, PoliciesFileUpload, $scope, $filter, $http, $sce, Upload) {
        var vm = this;
        vm.upload = upload;
        vm.clear = clear;
        vm.selectFile = selectFile;
        vm.fileUpload = '';
        vm.loadAll = loadAll;
        vm.policiesFileUpload;

        vm.loadAll();


        function loadAll() {

            $scope.$on('authenticationSuccess', function () {
                getAccount();
            });
        }

        getAccount();

        function getAccount() {
            Principal.identity().then(function (account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;

            });

        }

        var myDate = new Date();

        $scope.currentYear = $filter('date')(myDate, 'yyyy');


        function onError1() {


        }


        function upload() {

            var selectitem = $scope.selitem;
            vm.isSaving = true;

            console.log(("policiesFileUpload " + vm.policiesFileUpload.fileName +" "+ vm.policiesFileUpload.policyName ))
            Upload.upload({
                url: 'api/policies',
                data: {fileUploads: vm.fileUpload},
                params: {
                    filetype: 'Policies',
                    uploadfileName: vm.uploadfileName,
                    fileName: vm.policiesFileUpload.fileName,
                    policyName: vm.policiesFileUpload.policyName,
                    subject: vm.policiesFileUpload.subject
                }
            }).then(function (resp) {

                console.log("wewerwer" + resp)
                // if (resp.status == 201) {
                //
                // }
            }, function (resp) {
                console.log(resp);
            }, function (evt) {
                console.log(evt);

            });
        }


        function selectFile(file) {

            console.log(file);
            vm.fileUpload = file;
        }

        function clear() {
            $state.go($state.current.parent, {}, {
                reload: true
            });
        }


    }
})();


