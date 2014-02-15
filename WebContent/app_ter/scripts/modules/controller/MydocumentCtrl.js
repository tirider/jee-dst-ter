(function(){
	'use strict';
	var module = angular.module('mod.controller');

	module.controller('MydocumentCtrl', ['$scope', '$location','webStorage', 
	                                  function($scope, $location, webStorage){
		
		//Get session from webStorage
		$scope.$user_info = webStorage.session.get('$user_info');
		console.log($scope.$user_info);

	}]);
})();