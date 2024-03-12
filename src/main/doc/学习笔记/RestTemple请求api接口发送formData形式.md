### RestTemple请求api接口发送formData形式
```java
RestTemplate restTemplate = new RestTemplate();

// 用于请求报错后，查看报错详情的（如果接口有报错详情的话）
restTemplate.setErrorHandler(new ResponseErrorHandler() {
	@Override
	public boolean hasError(ClientHttpResponse clientHttpResponse) throws IOException {
		return false;
	}

	@Override
	public void handleError(ClientHttpResponse clientHttpResponse) throws IOException {

	}
});

// 用于formData请求方式
MultiValueMap<String,Object> params = new LinkedMultiValueMap<>();
params.add("key","value");

// MultiValueMap遇到需要填充list，可参考下面做法
for (int i = 0; i < AttachmentList.size(); i++) {            
	params.add("AttachmentList["+i+"].fileName",AttachmentList.get(i).getFileName());
	params.add("AttachmentList["+i+"].attachmentUrl",AttachmentList.get(i).getAttachmentUrl());
	params.add("AttachmentList["+i+"].fileSize",AttachmentList.get(i).getFileSize());
}

HttpHeaders headers = new HttpHeaders();
headers.setContentType(MediaType.MULTIPART_FORM_DATA);
HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(params, headers);
```