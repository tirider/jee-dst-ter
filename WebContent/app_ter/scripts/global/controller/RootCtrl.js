(function(){
	'use strict';
	var module = angular.module('global.controller');

	module.controller('RootCtrl', ['$scope', '$rootScope', 'webStorage', '$location',
	                               function($scope, $rootScope, webStorage, $location){
		
		//La gestion de spinner
		$scope.spinner = false;//Par defaut
		$scope.$on('spinnerOn', function(event, data){
			$scope.spinner = true;
			console.log('spinnerON');
		});
		$scope.$on('spinnerOff', function(event, data){
			$scope.spinner = false;
			console.log('spinnerOFF');
		});

		//La protection de session
		$scope.$on('$routeChangeStart', function(event, next, current){
			//Pour les routages qui ont besoins de session:
			if(webStorage.session.get('$user_info')==null&&next.$$route.controller!='InscriptionCtrl'){
				$location.path('/login');
			}
		});
	}]);
})();