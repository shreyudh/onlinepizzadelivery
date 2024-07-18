package com.onlinepizza.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.onlinepizza.dao.FoodDao;
import com.onlinepizza.model.Food;

@Controller
@MultipartConfig
public class FoodController {
	
	@Autowired
	private FoodDao foodDao;
	
	@GetMapping("/addfood")
	public String goToAddFoodPage() {
		return "addfood";
	}
	
	@GetMapping("/showfood")
	public ModelAndView goToShowFoodPage(@RequestParam("foodId") int foodId) {
		ModelAndView mv = new ModelAndView();
		Food food = null;
		Optional<Food> o = foodDao.findById(foodId);
		
		if(o.isPresent()) {
			food = o.get();
		}
		
		mv.setViewName("showfood");
		mv.addObject("food", food);
		
		return mv;
	}
	
	@PostMapping("/addfood")
	public ModelAndView addFood(HttpServletRequest request) throws IOException, ServletException {
		
		String name=request.getParameter("name");
		String description=request.getParameter("description");
		int storeId=Integer.parseInt(request.getParameter("storeId"));
		double price=Integer.parseInt(request.getParameter("price"));
		
		Part part=request.getPart("image");
		
		String fileName=part.getSubmittedFileName();
		
		String uploadPath="C:\\Users\\admin\\Desktop\\project\\online-pizza-ordering\\src\\main\\webapp\\resources\\foodImage\\"+fileName;
		
		try
		{
		FileOutputStream fos=new FileOutputStream(uploadPath);
		InputStream is=part.getInputStream();
		
		byte[] data=new byte[is.available()];
		is.read(data);
		fos.write(data);
		fos.close();
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
    ModelAndView mv = new ModelAndView();
	Food food = new Food();
	food.setImage(fileName);
	food.setName(name);
	food.setPrice(price);
	food.setStoreId(storeId);
	food.setDescription(description);
		
		if(this.foodDao.save(food) != null ) {
			mv.addObject("status", food.getName()+" Added Successfully!!!");
			mv.setViewName("index");
		}
		
		else {
			mv.addObject("status", food.getName()+" Failed to Add!!!");
			mv.setViewName("addfood");
		}
		
		return mv;
	}
	
	@PostMapping("/updatefood")
	public ModelAndView updateFood(HttpServletRequest request) throws IOException, ServletException {
		
		String name=request.getParameter("name");
		String description=request.getParameter("description");
		int storeId=Integer.parseInt(request.getParameter("storeId"));
		double price=Double.parseDouble(request.getParameter("price"));
		int id=Integer.parseInt(request.getParameter("id"));
		Part part=request.getPart("image");
		
		String fileName=part.getSubmittedFileName();
		
		String uploadPath="C:\\Users\\admin\\Desktop\\project\\online-pizza-ordering\\src\\main\\webapp\\resources\\foodImage\\"+fileName;
		
		try
		{
		FileOutputStream fos=new FileOutputStream(uploadPath);
		InputStream is=part.getInputStream();
		
		byte[] data=new byte[is.available()];
		is.read(data);
		fos.write(data);
		fos.close();
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
    ModelAndView mv = new ModelAndView();
	Food food = new Food();
	food.setId(id);
	food.setImage(fileName);
	food.setName(name);
	food.setPrice(price);
	food.setStoreId(storeId);
	food.setDescription(description);
		
		if(this.foodDao.save(food) != null ) {
			mv.addObject("status", food.getName()+" updated Successfully!!!");
			mv.setViewName("index");
		}
		
		else {
			mv.addObject("status", food.getName()+" Failed to Update!!!");
			mv.setViewName("index");
		}
		
		return mv;
	}
	
	@GetMapping("/searchfood")
	public ModelAndView searchFood(@RequestParam("foodname") String foodName) {
		ModelAndView mv = new ModelAndView();
		
		List<Food> foods = this.foodDao.findByNameContainingIgnoreCase(foodName);
		mv.addObject("food-source", "search");
			mv.addObject("foods", foods);
			mv.setViewName("index");
		
		return mv;
	}

	@GetMapping("/deletefood")
	public ModelAndView deleteFood(@RequestParam("foodId") int foodId) {
		ModelAndView mv = new ModelAndView();
		
		Optional<Food> oF = foodDao.findById(foodId);
	    Food food = null;
		
		if(oF.isPresent()) {
			food = oF.get();
		}
		
		this.foodDao.delete(food);
			mv.addObject("status", "Food deleted successfully!!!");
			mv.setViewName("index");
		
		return mv;
	}
	
	@GetMapping("/updatefood")
	public ModelAndView updateStore(@RequestParam("foodId") int foodId) {
		ModelAndView mv = new ModelAndView();
		
		Optional<Food> oC = foodDao.findById(foodId);
		Food food = null;
		
		if(oC.isPresent()) {
			food = oC.get();
		}
		
			mv.addObject("food",food);
			mv.setViewName("updatefood");
		
		return mv;
		
	}
	
}
