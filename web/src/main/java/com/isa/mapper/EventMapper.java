package com.isa.mapper;


import com.isa.domain.entity.Event;
import com.isa.domain.api.EventApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class EventMapper {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Inject
    public TicketMapper ticketMapper;

    @Inject
    public OrganizerMapper organizerMapper;

    @Inject
    public PlaceMapper placeMapper;

    @Inject
    public UrlMapper urlMapper;

    public Event mapApiViewToEntity(EventApi eventApiParser) {
        logger.info("Event mapping to Entity");

        Event event = new Event();

        event.setApiId(eventApiParser.getApi_Id());
        //event.setStartDate(eventApiParser.getStartDate());
        //event.setEndDate(eventApiParser.getEndDate());
        event.setDescShort(eventApiParser.getShortDescription());
        event.setName(eventApiParser.getName());
        event.setDescLong(eventApiParser.getLongDescription());
        event.setCategoryId(eventApiParser.getCategoryEventId());
        event.setActive(eventApiParser.getActiveEvent());


        return event;
    }

}