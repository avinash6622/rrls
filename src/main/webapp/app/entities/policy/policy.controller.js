(function() {
    'use strict';

    angular
        .module('researchRepositoryLearningSystemApp')
        .controller('PoliciesFileUploadController', PoliciesFileUploadController);

    PoliciesFileUploadController.$inject = ['PoliciesFileUpload', 'Principal','ParseLinks', 'AlertService', 'paginationConstants','$scope','$filter','pagingParams','$state','$sce','$http'];

    function PoliciesFileUploadController(PoliciesFileUpload,Principal, ParseLinks, AlertService, paginationConstants, $scope, $filter, pagingParams, $state, $sce,$http) {

        var vm = this;

        vm.policiesFileUpload = [];
        vm.loadAll = loadAll;
        vm.itemsPerPage = paginationConstants.itemsPerPage;
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.totalItems = null;
        vm.page = 1;
        vm.links = null;
        vm.reset = reset;
        vm.transition = transition;
        vm.itemsValue = 'Policies FileUpload';
        vm.deletePolicy=deletePolicy;
        vm.previewFileExtension = '';
        vm.loadPolicyPreview=loadPolicyPreview;
        vm.getPolicyFile = getPolicyFile;

        vm.loadAll();


        var myDate=new Date();

        $scope.currentYear = $filter('date')(myDate,'yyyy');

        function getPolicyFile(file) {
            console.log('getPolicyFile', file);
            return $http.get('/api/ur/policies/fileDownload/' + file.id).then(
                function (response) {
                    console.log(response.config.url);
                    window.location.href = response.config.url;
                }).catch(function (error) {
                    console.log(error);
                })
        }

        function loadAll () {
            PoliciesFileUpload.query({
                page: pagingParams.page - 1,
                size: vm.itemsPerPage,
                sort: sort()
            }, onSuccess, onError);

            $scope.$on('authenticationSuccess', function() {
                getAccount();});
        }
        function loadPolicyPreview(fileName) {
            console.log('file preview function invoked');
            var data = fileName;
            data = data.replace(/\\/g, "/");
            console.log('After slash replace - ' + data);

            let  urlValues = data.split('/');
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
        getAccount();

        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;

            });

        }
        function sort() {
            var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
            if (vm.predicate !== 'id') {
                result.push('id');
            }
            return result;
        }

        function onSuccess(data, headers) {
            vm.links = ParseLinks.parse(headers('link'));
            vm.totalItems = headers('X-Total-Count');

            vm.queryCount = vm.totalItems;
            vm.page = pagingParams.page;
            vm.policiesFileUpload = data;
        }

        function onError(error) {
            AlertService.error(error.data.message);
        }


        function reset () {
            vm.page = 0;
            vm.policiesFileUpload = [];
            loadAll();
        }

        function transition () {
            $state.transitionTo($state.$current, {
                page: vm.page,
                sort: vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc'),
                search: vm.currentSearch
            });
        }
        function deletePolicy(policiesFileUpload) {
            console.log("policiesFileUpload delete");
            console.log(policiesFileUpload);
            return $http.delete('/api/delete-PoliciesFileUpload/' + policiesFileUpload.id).then(
                function (response) {
                    console.log('response in delete');
                    console.log(response);
                    loadAll();
                }).catch(function (error) {
                console.log(error);
            });

        }


    }
})();





