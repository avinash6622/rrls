(function() {
    'use strict';

    angular
        .module('researchRepositoryLearningSystemApp')
        .controller('FileUploadCommentsDetailController', FileUploadCommentsDetailController);

    FileUploadCommentsDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'FileUploadComments', 'FileUpload', 'AdditionalFileUpload'];

    function FileUploadCommentsDetailController($scope, $rootScope, $stateParams, previousState, entity, FileUploadComments, FileUpload, AdditionalFileUpload) {
        var vm = this;

        vm.fileUploadComments = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('researchRepositoryLearningSystemApp:fileUploadCommentsUpdate', function(event, result) {
            vm.fileUploadComments = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
