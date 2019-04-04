(function () {
    'use strict';

    angular
        .module('researchRepositoryLearningSystemApp')
        .controller('CommunicationLettersDialogController', CommunicationLettersDialogController);

    CommunicationLettersDialogController.$inject = ['OpportunityMaster', 'Principal', '$state', 'CommunicationLetters', '$scope', '$filter', '$http', '$sce', 'Upload'];

    function CommunicationLettersDialogController(OpportunityMaster, Principal, $state, CommunicationLetters, $scope, $filter, $http, $sce, Upload) {
        var vm = this;
        vm.opportunityMasters = [];
        vm.oppMasterId = '';
        vm.upload = upload;
        vm.clear = clear;
        vm.selectFile = selectFile;
        vm.fileUpload = '';
        vm.loadAll = loadAll;
        vm.communicationLetters;

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


        vm.autoCompleteOpportunity = {
            minimumChars: 1,
            dropdownHeight: '200px',

            data: function (searchText) {
                return $http.get('api/opportunity-masters/all').then(
                    function (response) {
                        searchText = searchText.toLowerCase();
                        //  console.log(searchText);
                        // console.log(response);


                        // ideally filtering should be done on the server
                        var states = _.filter(response.data,
                            function (state) {
                                // console.log(state);
                                return (state.oppName).toLowerCase()
                                    .startsWith(searchText);

                            });

                        return states;
                    });
            },
            renderItem: function (item) {
                return {
                    value: item,
                    label: $sce.trustAsHtml("<p class='auto-complete'>"
                        + item.oppName + "</p>")
                };
            },

            itemSelected: function (e) {

                console.log(e);
                vm.selectedName = e;
                vm.name = e.item.oppName;
                vm.opportunityName = e.item;
                console.log(vm.opportunityName.id, 'NAme');


                OpportunityMaster.getSearchOpportunity(vm.opportunityName, onSuccess1, onError1);
            }
        }

        function onSuccess1(data, headers) {

            vm.opportunityMasters = data;
            vm.oppMasterId = vm.opportunityMasters[0].id;

        }

        function onError1() {


        }


        function upload() {

            var selectitem = $scope.selitem;
            vm.isSaving = true;
            Upload.upload({
                url: 'api/communication-letter',
                data: {fileUploads: vm.fileUpload},
                params: {
                    oppId: vm.oppMasterId,
                    filetype: 'Communication',
                    uploadfileName: vm.uploadfileName,
                    subject: vm.communicationLetter.subject
                }// {oppCode: inputData.oppCode, oppName: inputData.oppName, oppDescription: inputData.oppDescription, strategyMasterId: inputData.strategyMasterId.id}
            }).then(function (resp) {

                if (resp.status == 201) {

                }
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
