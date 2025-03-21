package com.massi.service;

import com.massi.model.Cart;
import com.massi.model.CartItem;
import com.massi.model.Food;
import com.massi.model.User;
import com.massi.repository.CartItemRepository;
import com.massi.repository.CartRepository;
import com.massi.repository.FoodRepository;
import com.massi.request.AddCartItemRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartServiceImp implements CartService{

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private FoodService foodService;

    @Override
    public CartItem addItemToCart(AddCartItemRequest req, String jwt) throws Exception {

        User user = userService.findUserByJwtToken(jwt);
        Food food = foodService.findFoodById(req.getFoodId());

        Cart cart = cartRepository.findByCustomerId(user.getId());

        for(CartItem cartItem : cart.getItems()){
            if (cartItem.getFood().getId() == food.getId()){
                //cartItem.setQuantity(cartItem.getQuantity() + req.getQuantity());
                int newQuantity = cartItem.getQuantity() + req.getQuantity();
                return updateCartItemQuantity(cartItem.getId(), newQuantity);
            }
        }

        CartItem cartItem = new CartItem();
        cartItem.setFood(food);
        cartItem.setQuantity(req.getQuantity());
        cartItem.setIngredients(req.getIngredients());
        cartItem.setTotalPrice(req.getQuantity()*food.getPrice());

        CartItem savedCartItem = cartItemRepository.save(cartItem);
        cart.getItems().add(savedCartItem);

        return savedCartItem;
    }

    @Override
    public CartItem findCartItemById(Long id) throws Exception {
        Optional<CartItem> cartItemOpt = cartItemRepository.findById(id);

        if (cartItemOpt.isEmpty()) {
            throw new Exception("CartItem not found");
        }

        return cartItemOpt.get();
    }

    @Override
    public CartItem updateCartItemQuantity(Long cartItemId, int quantity) throws Exception {

        CartItem item = findCartItemById(cartItemId);

        //item.setQuantity(item.getQuantity() + quantity);
        item.setQuantity(quantity);
        item.setTotalPrice(item.getFood().getPrice()*quantity);

        return cartItemRepository.save(item);
    }

    @Override
    public Cart removeItemFromCart(Long cartItemId, String jwt) throws Exception {

        User user = userService.findUserByJwtToken(jwt);
        Cart cart = cartRepository.findByCustomerId(user.getId());

        CartItem item = findCartItemById(cartItemId);

        cart.getItems().remove(item);

        return cartRepository.save(cart);
    }

    @Override
    public Long calculateCartTotals(Cart cart) throws Exception {

        if (cart == null || cart.getItems() == null) {
            throw new Exception("Cart is empty or null");
        }

        Long total = 0L;

        for (CartItem cartItem : cart.getItems()) {
            total += cartItem.getFood().getPrice() * cartItem.getQuantity();
        }

        return total;
    }

    @Override
    public Cart findCartById(Long id) throws Exception {

        Optional<Cart> cartOpt = cartRepository.findById(id);
        if (cartOpt.isEmpty()) {
            throw new Exception("CartItem not found");
        }

        return cartOpt.get();
    }

    @Override
    public Cart findCartByUserId(Long userId) throws Exception {

        Cart cart = cartRepository.findByCustomerId(userId);
        cart.setTotal(calculateCartTotals(cart));

        return cartRepository.save(cart);
    }

    @Override
    public Cart clearCart(Long userId) throws Exception {

        Cart cart = findCartByUserId(userId);
        cart.getItems().clear();

        return cartRepository.save(cart);
    }
}
