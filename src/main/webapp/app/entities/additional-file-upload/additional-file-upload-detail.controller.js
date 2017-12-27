(function() {
    'use strict';

    angular
        .module('researchRepositoryLearningSystemApp')
        .controller('AdditionalFileUploadDetailController', AdditionalFileUploadDetailController);

    AdditionalFileUploadDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'AdditionalFileUpload', 'FileUpload'];

    function AdditionalFileUploadDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, AdditionalFileUpload, FileUpload) {
        var vm = this;

        vm.additionalFileUpload = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('researchRepositoryLearningSystemApp:additionalFileUploadUpdate', function(event, result) {
            vm.additionalFileUpload = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
