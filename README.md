# Smart-Surveillance-System
	In modern surveillance systems the recorded video is deleted when the storage is full. Normally people use 
	1TB or 512GB hard drives for video storage. In a case where the recorded video is needed and that recorded 
	video is needed you cannot get that video. Because even you can’t recover the data when they are overwritten.
	And most of the times surveillance systems records video uselessly on 24x7. This makes system to run wasting 
	power and resources. Smart Surveillance System(SSS) gives solution to these issues.

Description of proposed system 

	A video is recorded if a motion is recognized inside a short video clip. By doing a semantic analysis of that 
	short clip we have to analyze it. We have to concentrate on three main factors. 

	1. Background and foreground separation 
	To analyze the motion, we have to separate background and the foreground and by that we can think about the
	motion independent of the background. 

	2. Moving blob identification 
	In a video if there is a motion there is a change in the byte arrangement in continuous set of images. This is 
	called moving blobs. To detect the motion, we have to think about the moving blobs. 

	3. Motion identification 
	We have to analyze and recognize the type of the motion as well as the origin of the motion. This is to identify 
	the motion source and what the motion is.
