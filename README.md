File Upload using Dropzone.js and angularJS
=====================================

##Introduction

The whole angularJS code exists in app/assets/javascripts/app.js

We are creating a directive named drop-zone and a controller to test this directive named UploadCtrl. 


### Usage

  <div drop-zone
    upload="{{ uploadURL }}"
    done="uploadFinished(file, response)">
  </div>

Where uploadURL is the url to uplaod the files (coming from the controller) and done is the callback when the file upload is finished successfully. 

### Directive:

  .directive('dropZone', function() {
    return {
      restrict: 'A',
      scope: {
        upload: '@',
        done: '&'
      },
      template : '<div id="upload-dropzone" class="upload-box" > </div>',
      replace: true,
      link: function(scope, element) {
        var optionsObj = {
          maxFilesize: 1024
        };

        if(scope.done) {
          optionsObj.success = function(file, response) {
            scope.$apply(function() {
              scope.done({file: file, response: response});
            });
          };
        }

        scope.$watch('upload', function(oldValue, newValue) {
          optionsObj.url = newValue;
          element.dropzone(optionsObj);
        });
      }
    };
  })

We will replace the directive with the template, thus we have the replace flag to true. This is just to keep the DOM small.

In the link function we are creating a option object for the dropzone check the [http://www.dropzonejs.com/#configure](http://www.dropzonejs.com/#configure) url for full options. Keep in mind that if we are configuring events (like the success event) then we need to inform angularJS that the event has changed value, for that we need to call the scope.$apply method.

We want to add the upload url programmatically. When we are creating the dropzone it will take the url from either the action or the options and configure the dropzone with this value. However at the link phase angular hasn't evaluated the upload variable yet so if we just call the element.dropzone at that point we will have as the url the string { { uploadURL  } }. to solve this we need to create the dropzone once the upload variable has been evaluated, so we need to call a $watch function on 'upload' variable and create the dropzone once the url is set up. 




