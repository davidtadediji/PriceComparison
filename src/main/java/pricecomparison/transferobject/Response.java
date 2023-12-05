/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pricecomparison.transferobject;

/**
 *
 * @author David
 */
public class Response {

    private final String content;
    private final int statusCode;

    public Response(String content, int statusCode) {
        this.content = content;
        this.statusCode = statusCode;
    }

    public String getHtmlContent() {
        return content;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
