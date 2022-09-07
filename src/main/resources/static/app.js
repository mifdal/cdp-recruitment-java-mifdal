'use strict';

angular.module('myevent', [
    'angular-input-stars'
])
.factory('EventService', EventService)
.controller('EventsController', EventsController);

function EventService($http){
    return {
        deleteEvent:deleteEvent,
        getEvents:getEvents,
        getFiltredEvents:getFiltredEvents,
        updateStars: updateStars
    };

    function deleteEvent(id){
        return $http.delete('/api/events/' + id);
    }

    function getEvents(){
        return $http.get('/api/events/')
            .then(getEventsComplete);

        function getEventsComplete(response){
            return response.data;
        }
    }

    function getFiltredEvents(query){
        return $http.get('/api/events/search/'+query)
            .then(getFiltredEventsComplete);

        function getFiltredEventsComplete(response){
            return response.data;
        }
    }

    function updateStars(event){
        return $http.put('/api/events/' + event.id, event);
    }
}

function EventsController(EventService){
    var vm = this;
    vm.deleteEvent = deleteEvent;
    vm.updateStars = updateStars;
    vm.getFiltredEvents = getFiltredEvents;

    activate();

    function activate() {
        return EventService.getEvents()
        .then(function(events) {
            vm.events = events;
            return vm.events;
        });
    }

    function deleteEvent(event){
        var index = vm.events.indexOf(event);
        return EventService.deleteEvent(event.id)
            .then(function() {
                vm.events.splice(index, 1);
            });
    }

    function updateStars(event){
        return EventService.updateStars(event);
    }

    function getFiltredEvents(query){
        if (query == "") {
            return activate()
        }else {
            return EventService.getFiltredEvents(query)
                .then(function(events) {
                    vm.events = events;
                    return vm.events;
                });
        }
    }
}
