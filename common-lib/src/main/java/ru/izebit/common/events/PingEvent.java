package ru.izebit.common.events;


import lombok.Data;

/**
 * @author <a href="mailto:izebit@gmail.com">Artem Konovalov</a> <br/>
 * Date: 28.10.2022
 */
@Data
public class PingEvent {
    private final String msg = "ping";
}
