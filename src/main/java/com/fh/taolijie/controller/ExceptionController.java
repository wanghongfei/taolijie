package com.fh.taolijie.controller;

import com.fh.taolijie.exception.unchecked.EntityNotExistException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Created by wynfrith on 15-4-8.
 */
@Controller
public class ExceptionController {
    public class SimpleController {

        // @RequestMapping methods omitted ...

        @ExceptionHandler(EntityNotExistException.class)
        public String  handleIOException(EntityNotExistException ex) {
            System.out.println("++++++++++抛出异常++++++++++");
            System.out.println(ex);

            return "redirect:/404";
        }

    }
}
