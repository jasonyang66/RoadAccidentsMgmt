package com.epam.processor;

import com.epam.data.RoadAccident;
import com.epam.data.RoadAccidentBuilder;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.util.*;
import java.util.Map.*;
import java.util.stream.Collectors;

/**
 * This is to be completed by mentees jason yang
 */
public class DataProcessor {

    private final List<RoadAccident> roadAccidentList;

    public DataProcessor(List<RoadAccident> roadAccidentList){
        this.roadAccidentList = roadAccidentList;
    }


//    First try to solve task using java 7 style for processing collections

    /**
     * Return road accident with matching index
     * @param index
     * @return
     */
    public RoadAccident getAccidentByIndex7(String index){
        RoadAccidentBuilder builder = new RoadAccidentBuilder(index);
        return builder.build();
    }


    /**
     * filter list by longtitude and latitude values, including boundaries
     * @param minLongitude
     * @param maxLongitude
     * @param minLatitude
     * @param maxLatitude
     * @return
     */
    public Collection<RoadAccident> getAccidentsByLocation7(float minLongitude, float maxLongitude, float minLatitude, float maxLatitude){
        Collection<RoadAccident> roadAccidentList = new ArrayList<RoadAccident>();
        if(this.roadAccidentList.size()>0) {
            for(RoadAccident roadAccident:roadAccidentList){
                if(roadAccident.getLongitude() < minLongitude || roadAccident.getLatitude() < minLatitude
                        || roadAccident.getLongitude() > maxLongitude || roadAccident.getLatitude() > maxLatitude) {
                    continue;
                } else {
                    roadAccidentList.add(roadAccident);
                }
            }
            return roadAccidentList;
        }
        return null;
    }

    /**
     * count incidents by road surface conditions
     * ex:
     * wet -> 2
     * dry -> 5
     * @return
     */
    public Map<String, Long> getCountByRoadSurfaceCondition7(){
        Map<String,Long> roadSurfaceMap = new TreeMap<String,Long>();
        Long firstElement = 1L;
        if(this.roadAccidentList.size()>0) {
            for(RoadAccident roadAccident:roadAccidentList) {
                String surfaceCondition = roadAccident.getRoadSurfaceConditions();
                Long count = roadSurfaceMap.get(surfaceCondition);
                if(roadSurfaceMap.containsKey(surfaceCondition)) {
                    count++;
                    roadSurfaceMap.put(surfaceCondition,count);
                } else {
                    roadSurfaceMap.put(surfaceCondition,firstElement);
                }
            }
            return roadSurfaceMap;
        }
        return null;
    }

    private static Map<String, Long> sortByComparator(Map<String, Long> unsortMap, final boolean order) {
        List<Entry<String, Long>> list = new LinkedList<Entry<String, Long>>(unsortMap.entrySet());
        Collections.sort(list, new Comparator<Entry<String, Long>>()
        {
            public int compare(Entry<String, Long> o1,
                               Entry<String, Long> o2)
            {
                if (order)
                {
                    return o1.getValue().compareTo(o2.getValue());
                }
                else
                {
                    return o2.getValue().compareTo(o1.getValue());

                }
            }
        });

        // Maintaining insertion order with the help of LinkedList
        Map<String, Long> sortedMap = new LinkedHashMap<String, Long>();
        for (Entry<String, Long> entry : list)
        {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;

    }

        /**
         * find the weather conditions which caused the top 3 number of incidents
         * as example if there were 10 accidence in rain, 5 in snow, 6 in sunny and 1 in foggy, then your result list should contain {rain, sunny, snow} - top three in decreasing order
         * @return
         */
    public List<String> getTopThreeWeatherCondition7(){


        Map<String,Long> roadSurfaceMap = new TreeMap<String,Long>();
        Long firstElement = 1L;
        if(this.roadAccidentList.size()>0) {
            for(RoadAccident roadAccident:roadAccidentList) {
                String weatherCondition = roadAccident.getWeatherConditions();
                Long count = roadSurfaceMap.get(weatherCondition);
                if(roadSurfaceMap.containsKey(weatherCondition)) {
                    count++;
                    roadSurfaceMap.put(weatherCondition,count);
                } else {
                    roadSurfaceMap.put(weatherCondition,firstElement);
                }
            }
            sortByComparator(roadSurfaceMap,false);
            return new ArrayList<String>(roadSurfaceMap.keySet()).subList(0,3);
        }
        return null;
    }

    /**
     * return a multimap where key is a district authority and values are accident ids
     * ex:
     * authority1 -> id1, id2, id3
     * authority2 -> id4, id5
     * @return
     */
    public Multimap<String, String> getAccidentIdsGroupedByAuthority7(){
        Multimap<String,String> multiMap = ArrayListMultimap.create();
        if(this.roadAccidentList.size()>0) {
            for (RoadAccident roadAccident : roadAccidentList) {
                multiMap.put(roadAccident.getDistrictAuthority(),roadAccident.getAccidentId());
            }
            return multiMap;
        }
        return null;
    }


    // Now let's do same tasks but now with streaming api



    public RoadAccident getAccidentByIndex(String index){
        RoadAccident roadAccident = this.roadAccidentList.stream().filter(item -> index.equals(item.getAccidentId())).findFirst().orElse(null);
        return roadAccident;
    }


    /**
     * filter list by longtitude and latitude fields
     * @param minLongitude
     * @param maxLongitude
     * @param minLatitude
     * @param maxLatitude
     * @return
     */
    public Collection<RoadAccident> getAccidentsByLocation(float minLongitude, float maxLongitude, float minLatitude, float maxLatitude){
        if(this.roadAccidentList.size()>0) {
          return roadAccidentList.stream().filter(roadAccident->roadAccident.getLongitude() >= minLongitude)
                    .filter(roadAccident->roadAccident.getLongitude()<=maxLongitude)
                    .filter(roadAccident->roadAccident.getLatitude()>=minLatitude)
                    .filter(roadAccident->roadAccident.getLatitude()<=maxLatitude).collect(Collectors.toList());
        }
        return null;
    }

    /**
     * find the weather conditions which caused max number of incidents
     * @return
     */
    public List<String> getTopThreeWeatherCondition(){
        if(this.roadAccidentList.size()>0) {
            return roadAccidentList.stream().collect(
                    Collectors.groupingBy(RoadAccident::getWeatherConditions,Collectors.counting())).
                    entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).
                    limit(3).map(roadAccident -> roadAccident.getKey()).collect(Collectors.toList());
        }
        return null;
    }

    /**
     * count incidents by road surface conditions
     * @return
     */
    public Map<String, Long> getCountByRoadSurfaceCondition(){
        return roadAccidentList.stream().collect(
                Collectors.groupingBy(RoadAccident::getRoadSurfaceConditions, Collectors.counting()));
    }

    /**
     * To match streaming operations result, return type is a java collection instead of multimap
     * @return
     */
    public Map<String, List<String>> getAccidentIdsGroupedByAuthority(){
        return roadAccidentList.stream().collect(Collectors.groupingBy(RoadAccident::getDistrictAuthority,
                Collectors.mapping(RoadAccident::getAccidentId, Collectors.toList())));
    }

}
