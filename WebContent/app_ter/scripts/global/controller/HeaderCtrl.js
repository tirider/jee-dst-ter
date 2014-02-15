(function(){
	'use strict';
	var module = angular.module('global.controller');

	module.controller('HeaderCtrl', ['$scope', '$rootScope', 'cmWSFacade','webStorage', '$location',
	                                 function($scope, $rootScope, cmWSFacade, webStorage, $location){
		$scope.signOut = function(){
			webStorage.session.clear();
			$location.path('/login');
		};
		
		//Get session from webStorage
		$scope.$user_info = webStorage.session.get('$user_info');
		
	}]);
})();