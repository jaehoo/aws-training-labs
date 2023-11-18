package s3_upload;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * aws lambda function that creates a pre-signed URL for uploading an image and returns this URL to the caller.
 * The bucket will be retrieved from the environment variables
 */
public class Handler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, com.amazonaws.services.lambda.runtime.Context context) {
        LambdaLogger logger = context.getLogger();
        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();

        try {
                String body = input.getBody();
            logger.log("Body: " + body);
            Map<String, String> bodyMap = new HashMap<>();

            bodyMap = (Map<String, String>) gson.fromJson(body, Map.class);
            String fileName = bodyMap.get("file");

            if (fileName == null) {
                throw new IllegalArgumentException("Invalid body: "+ body);
            }

            // ***** TASK 1 - Create an Amazon S3 pre-signed URL with the filename as the bucket key
            String bucketName = System.getenv("BUCKET");
            String presignedurl = "";
            
            AmazonS3 s3Client = AmazonS3ClientBuilder.defaultClient();
            GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, fileName);
            generatePresignedUrlRequest.withMethod(HttpMethod.PUT);
            //generatePresignedUrlRequest.setExpiration(new Date(System.currentTimeMillis() + 10 * 60 * 1000));
            URL presignedUriUrl = s3Client.generatePresignedUrl(generatePresignedUrlRequest);
            presignedurl= presignedUriUrl.toURI().toString();


            Map<String, String> headers = new HashMap<>();
            headers.put("Access-Control-Allow-Origin", "*");
            response.withStatusCode(200).withHeaders(headers).withBody(presignedurl);
            logger.log("URL: " + presignedurl);
        } catch (Exception e) {
            e.printStackTrace();
            response.withStatusCode(500).withBody(e.getMessage());
        }
        return response;
    }
}
