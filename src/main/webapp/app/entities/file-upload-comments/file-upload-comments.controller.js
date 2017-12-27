(function() {
    'use strict';

    angular
        .module('researchRepositoryLearningSystemApp')
        .controller('FileUploadCommentsController', FileUploadCommentsController);

    FileUploadCommentsController.$inject = ['FileUploadComments', 'ParseLinks', 'AlertService', 'paginationConstants'];

    function FileUploadCommentsController(FileUploadComments, ParseLinks, AlertService, paginationConstants) {

        var vm = this;

        vm.fileUploadComments = [];
        vm.loadPage = loadPage;
        vm.itemsPerPage = paginationConstants.itemsPerPage;
        vm.page = 0;
        vm.links = {
            last: 0
        };
        vm.predicate = 'id';
        vm.reset = reset;
        vm.reverse = true;

        loadAll();

        function loadAll () {
            FileUploadComments.query({
                page: vm.page,
                size: vm.itemsPerPage,
                sort: sort()
            }, onSuccess, onError);
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
                for (var i = 0; i < data.length; i++) {
                    vm.fileUploadComments.push(data[i]);
                }
            }

            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        function reset () {
            vm.page = 0;
            vm.fileUploadComments = [];
            loadAll();
        }

        function loadPage(page) {
            vm.page = page;
            loadAll();
        }
    }
})();
