<?php

$url = 'https://android.googleapis.com/gcm/send';

$registration_id = 'APA91bFhQH5NSRzasbZc7cy951DDttmHI0jg49QE-wz8HIC-ebzijUu5va03C1jN7v-2LPF5lS5TInXf5W8CEHvGhyjaV2SwNpTCrE2Ra3A5cdSDaEZXJmdrHxT1zxkvzHwMurQI-VWym5rBdLqoBitM8cw4SiwG1BuGj69LCYrlEYMdOisIGwo'; //registration IDはここ
$message = 'Hello, GCM!!';

$header = array(
  'Content-Type: application/x-www-form-urlencoded;charset=UTF-8',
  'Authorization: key=AIzaSyAyPSzZ1-24K6aCwu4JZsebVBQ-YdkcfRA', //API keyはここ
);
$post_list = array(
  'registration_id' => $registration_id,
  'collapse_key' => 'update',
  'data.message' => $message,
);
$post = http_build_query($post_list, '&');

$ch = curl_init($url);
curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
curl_setopt($ch, CURLOPT_FAILONERROR, 1);
curl_setopt($ch, CURLOPT_FOLLOWLOCATION, 1);
curl_setopt($ch, CURLOPT_POST, TRUE);
curl_setopt($ch, CURLOPT_HTTPHEADER, $header);
curl_setopt($ch, CURLOPT_POSTFIELDS, $post);
curl_setopt($ch, CURLOPT_TIMEOUT, 5);

//CA証明書の検証をしない
curl_setopt($ch,CURLOPT_SSL_VERIFYPEER,false);

$ret = curl_exec($ch);

//var_dump($ret);

?>
