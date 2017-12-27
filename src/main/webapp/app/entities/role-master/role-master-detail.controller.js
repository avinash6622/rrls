(function() {
    'use strict';

    angular
        .module('researchRepositoryLearningSystemApp')
        .controller('RoleMasterDetailController', RoleMasterDetailController);

    RoleMasterDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'RoleMaster'];

    function RoleMasterDetailController($scope, $rootScope, $stateParams, previousState, entity, RoleMaster) {
        var vm = this;

        vm.roleMaster = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('researchRepositoryLearningSystemApp:roleMasterUpdate', function(event, result) {
            vm.roleMaster = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
