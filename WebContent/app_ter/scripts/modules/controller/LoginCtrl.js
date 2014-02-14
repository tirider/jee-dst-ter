(function(){
	'use strict';
	var module = angular.module('mod.controller');
	module.controller('LoginCtrl', ['$scope', '$location', function($scope, $location){
		console.log('login');
		$scope.log = 'nihao';
	}]);
})();