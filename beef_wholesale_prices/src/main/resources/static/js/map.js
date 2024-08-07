let mapContainer = document.getElementById('map'),
    mapOption = {
      center: new kakao.maps.LatLng(36.0, 127.5),
      level: 13
    };

let map = new kakao.maps.Map(mapContainer, mapOption),
    customOverlay = new kakao.maps.CustomOverlay({});

let detailMode = false;
let polygons = [];
let areas = [];

// AJAX 요청으로 GeoJSON 데이터 불러오기
$.getJSON('korea.json', function(geojson) {
  init(geojson);
});

function init(geojson) {
  var units = geojson.features;

  units.forEach((unit, index) => {
    var coordinates = unit.geometry.coordinates[0];
    var name = unit.properties.SIG_KOR_NM;

    var ob = {
      name: name,
      path: coordinates[0].map(coordinate => new kakao.maps.LatLng(coordinate[1], coordinate[0]))
    };

    areas[index] = ob;
  });

  areas.forEach(area => displayArea(area));
}

function displayArea(area) {
  var polygon = new kakao.maps.Polygon({
    map: map,
    path: area.path,
    strokeWeight: 2,
    strokeColor: '#004c80',
    strokeOpacity: 0.8,
    fillColor: '#fff',
    fillOpacity: 0.7
  });

  polygons.push(polygon);

  kakao.maps.event.addListener(polygon, 'mouseover', function(mouseEvent) {
    polygon.setOptions({fillColor: '#09f'});
    customOverlay.setContent('<div class="area">' + area.name + '</div>');
    customOverlay.setPosition(mouseEvent.latLng);
    customOverlay.setMap(map);
  });

  kakao.maps.event.addListener(polygon, 'mousemove', function(mouseEvent) {
    customOverlay.setPosition(mouseEvent.latLng);
  });

  kakao.maps.event.addListener(polygon, 'mouseout', function() {
    polygon.setOptions({fillColor: '#fff'});
    customOverlay.setMap(null);
  });

  kakao.maps.event.addListener(polygon, 'click', function(mouseEvent) {
    var latlng = mouseEvent.latLng;
    map.panTo(latlng);
  });
}