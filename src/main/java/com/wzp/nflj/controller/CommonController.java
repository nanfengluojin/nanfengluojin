package com.wzp.nflj.controller;

import com.wzp.nflj.config.CustomConfig;
import com.wzp.nflj.enums.ResultCodeEnum;
import com.wzp.nflj.util.*;
import com.wzp.nflj.util.fileUpload.util.ChunkUploadUtil;
import com.wzp.nflj.util.fileUpload.vo.CheckMd5FileVO;
import com.wzp.nflj.util.fileUpload.vo.UploadVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: zp.wei
 * @DATE: 2020/9/2 18:15
 */
@Api(tags = "通用接口管理")
@Slf4j
@RestController
@RequestMapping("/common")
public class CommonController {


    @Resource
    @Qualifier("consumerTokenServices")
    private ConsumerTokenServices consumerTokenServices;
    @Resource
    private TokenEndpoint tokenEndpoint;
    @Resource
    protected HttpServletRequest request;
    @Resource
    protected HttpServletResponse response;


    @ApiOperation("获取系统时间")
    @GetMapping("/getTime")
    public Result getTime() {
        return Result.ok(DateUtil.sysTime());
    }


    @ApiOperation("刷新token")
    @GetMapping("/refreshToken")
    public Result refreshToken(@RequestHeader Map<String, Object> headerMap) {
        Map<String, String> parameters = new HashMap<>(2);
        parameters.put("grant_type", "refresh_token");//设置授权类型为刷新token
        try {
            String refreshToken = String.valueOf(headerMap.get("refresh_token"));
            parameters.put("refresh_token", refreshToken);
            Authentication authentication = new UsernamePasswordAuthenticationToken(CustomConfig.withClient, CustomConfig.secret, null);
            ResponseEntity<OAuth2AccessToken> responseEntity = tokenEndpoint.postAccessToken(authentication, parameters);
            return Result.ok(responseEntity.getBody());
        } catch (Exception e) {
            return Result.error(ResultCodeEnum.FORBIDDEN);
        }
    }


    @ApiOperation("退出登录,并清除redis中的token")
    @GetMapping("/loginOut")
    public Result loginOut(@RequestHeader Map<String, Object> headerMap) {
        if (ObjUtil.isEmpty(headerMap.get("access_token"))) {
            return Result.error(ResultCodeEnum.PARAM_ERROR);
        }
        consumerTokenServices.revokeToken(String.valueOf(headerMap.get("access_token")));
        return Result.ok();
    }


    @ApiOperation("查看登录用户权限")
    @GetMapping("/getAuth")
    public Result getAuth() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Map<String, Object> map = new HashMap<>(1);
        map.put("authentication", authentication.getAuthorities());
        return Result.ok(map);
    }


    /**
     * 获取验证码
     *
     * @return
     * @throws Exception
     */
    @GetMapping("/getCode")
    public Result getCode() {
        //第一个参数是生成的验证码，第二个参数是生成的图片
        Object[] objs = CodeUtil.createImage();
        //将生成的验证码发送到前端
        String codes = (String) objs[0];
        //将图片输出给浏览器
        BufferedImage image = (BufferedImage) objs[1];
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "png", out);
        } catch (IOException e) {
            log.error("验证码错误:" + e.getMessage());
            return Result.error(ResultCodeEnum.SYSTEM_ERROR);
        }
        byte[] bytes = out.toByteArray();
        Map<String, Object> map = new HashMap<>(2);
        map.put("codes", codes);
        map.put("image", bytes);
        return Result.ok(map);
    }


    /**
     * 根据数据生成二维码
     *
     * @throws IOException
     */
    @GetMapping("qrCode")
    @ApiOperation("根据传过来的数据生成二维码")
    @ApiImplicitParam(name = "code", example = "0874465465465", value = "生成二维码的数据")
    public void QrCode() throws IOException {
        ServletOutputStream stream = null;
        String code = request.getParameter("code");
        try {
            stream = response.getOutputStream();
            //使用工具类生成二维码 判断是否有logo图片的路径，若有则生成带logo的二维码
            if (ObjUtil.isEmpty(request.getParameter("logoPath"))) {
                QRCodeUtil.encode(code, stream);
            } else {
                String logoPath = request.getParameter("logoPath");
                QRCodeUtil.encode(code, logoPath, stream, true);
            }
        } catch (Exception e) {
            e.getStackTrace();
        } finally {
            if (stream != null) {
                stream.flush();
                stream.close();
            }
        }
    }


    /**
     * 文件检查
     *
     * @param md5FileVO
     * @return
     */
    @PostMapping("/check")
    @ApiOperation("文件上传检查")
    public Result check(@RequestBody CheckMd5FileVO md5FileVO) {
        return ChunkUploadUtil.check(md5FileVO);
    }


    /**
     * 文件上传
     *
     * @param file
     * @param uploadVO
     * @return
     */
    @PostMapping("/fileUpload")
    @ApiOperation("文件上传")
    public Result fileUpload(@RequestParam("file") MultipartFile file, UploadVO uploadVO) {
        return ChunkUploadUtil.upload(file, uploadVO);
    }


}
