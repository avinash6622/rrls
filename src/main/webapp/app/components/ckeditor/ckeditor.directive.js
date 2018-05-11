(function () {
    'use strict';

    angular
        .module('researchRepositoryLearningSystemApp')
        .directive('ckeditorDir', Directive);

        Directive.$inject = ['$rootScope'];

    function Directive($rootScope) {
        return {
            require: 'ngModel',
            link: function (scope, element, attr, ngModel) {
            console.log(element);
                var editorOptions;
                if (attr.ckeditor === 'minimal') {
                    // minimal editor
                    editorOptions = {
                        height: 100,
                        toolbar: [
                            { name: 'basic', items: ['Bold', 'Italic', 'Underline'] },
                            { name: 'links', items: ['Link', 'Unlink'] },
                            { name: 'tools', items: ['Maximize'] },
                            { name: 'document', items: ['Source'] },
                        ],
                        removePlugins: 'elementspath',
                        resize_enabled: false
                    };
                } else {
                    // regular editor
                    editorOptions = {
                        filebrowserImageUploadUrl:  '/upload',
                        removeButtons: 'About,Form,Checkbox,Radio,TextField,Textarea,Select,Button,ImageButton,HiddenField,Save,CreateDiv,Language,BidiLtr,BidiRtl,Flash,Iframe,addFile,Styles',
                        extraPlugins: 'simpleuploads,imagesfromword'
                    };
                }

                // enable ckeditor
                var ckeditor = element.ckeditor(editorOptions);

                // update ngModel on change
                ckeditor.editor.on('change', function () {
                    //console.log(this.getData());
                    ngModel.$setViewValue(this.getData());
                });
            }
        };
    }
})();
