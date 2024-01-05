/*
 *
 * You can use the following import statements
 * 
 * import org.springframework.beans.factory.annotation.Autowired;
 * import org.springframework.http.HttpStatus;
 * import org.springframework.stereotype.Service;
 * import org.springframework.web.server.ResponseStatusException;
 * import java.util.ArrayList;
 * import java.util.List;
 * 
 */

// Write your code here
package com.example.nxtstayz.service;

import com.example.nxtstayz.model.*;
import com.example.nxtstayz.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.ArrayList;
import java.util.List;

@Service
public class RoomJpaService implements RoomRepository {

    @Autowired
    private RoomJpaRepository roomJpaRepository;

    @Autowired
    private HotelJpaService hotelJpaService;

    @Override
    public ArrayList<Room> getRooms() {
        List<Room> roomsList = roomJpaRepository.findAll();
        ArrayList<Room> rooms = new ArrayList<>(roomsList);
        return rooms;
    }

    @Override
    public Room getRoomById(int roomId) {
        try {
            Room room = roomJpaRepository.findById(roomId).get();
            return room;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Room addRoom(Room room) {
        int hotelId = room.getHotel().getHotelId();
        Hotel hotel = hotelJpaService.getHotelById(hotelId);
        room.setHotel(hotel);
        roomJpaRepository.save(room);
        return room;
    }

    @Override 
    public Room updateRoom(int roomId, Room room) {
        try {
            Room newRoom = roomJpaRepository.findById(roomId).get();
            if(room.getHotel() != null) {
                int hotelId = room.getHotel().getHotelId();
                Hotel hotel = hotelJpaService.getHotelById(hotelId);
                newRoom.setHotel(hotel);
            }
            if(room.getRoomNumber() != null) {
                newRoom.setRoomNumber(room.getRoomNumber());
            }
            if(room.getType() != null) {
                newRoom.setType(room.getType());
            }
            if(room.getPrice() != 0) {
                newRoom.setPrice(room.getPrice());
            }
            roomJpaRepository.save(newRoom);
            return newRoom;
        }catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
    @Override 
    public void deleteRoom(int roomId) {
        try {
            roomJpaRepository.deleteById(roomId);
        }catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }

    @Override 
    public Hotel getRoomHotel(int roomId) {
        try {
            Room room = roomJpaRepository.findById(roomId).get();
            Hotel hotel = room.getHotel();
            return hotel;
        }catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}