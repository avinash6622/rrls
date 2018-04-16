(function() {
    'use strict';

    var jhiItemCount = {
        template: '<div class="info" data-translate="global.item-count" ' +
        'translate-value-first="{{(($ctrl.page - 1) * $ctrl.itemsPerPage) == 0 ? 1 : (($ctrl.page - 1) * $ctrl.itemsPerPage + 1)}}" ' +
        'translate-value-second="{{($ctrl.page * $ctrl.itemsPerPage) < $ctrl.queryCount ? ($ctrl.page * $ctrl.itemsPerPage) : $ctrl.queryCount}}" ' +
        'translate-value-total="{{$ctrl.queryCount}}"' +
        'translate-value-item="{{$ctrl.itemsValue}}">' +
        'Showing {{(($ctrl.page - 1) * $ctrl.itemsPerPage) == 0 ? 1 : (($ctrl.page - 1) * $ctrl.itemsPerPage + 1)}} - ' +
        '{{($ctrl.page * $ctrl.itemsPerPage) < $ctrl.queryCount ? ($ctrl.page * $ctrl.itemsPerPage) : $ctrl.queryCount}} ' +
        'of {{$ctrl.queryCount}} {{$ctrl.itemsValue}}' +
        '</div>',
        bindings: {
            page: '<',
            queryCount: '<total',
            itemsPerPage: '<',
            itemsValue:'<item'
        }
    };

    angular
        .module('researchRepositoryLearningSystemApp')
        .component('jhiItemCount', jhiItemCount);
})();
