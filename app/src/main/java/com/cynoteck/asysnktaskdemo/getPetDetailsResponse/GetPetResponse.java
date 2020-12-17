package com.cynoteck.asysnktaskdemo.getPetDetailsResponse;

import com.cynoteck.asysnktaskdemo.Header;
import com.cynoteck.asysnktaskdemo.Response;

public class GetPetResponse {
    private GetPetData data;

    private Response response;

    private Header header;

    public GetPetData getData ()
    {
        return data;
    }

    public void setData (GetPetData data)
    {
        this.data = data;
    }

    public Response getResponse ()
    {
        return response;
    }

    public void setResponse (Response response)
    {
        this.response = response;
    }

    public Header getHeader ()
    {
        return header;
    }

    public void setHeader (Header header)
    {
        this.header = header;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [data = "+data+", response = "+response+", header = "+header+"]";
    }
}
