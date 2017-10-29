<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:p="http://www.coffeeShop.com.vn"   version="1.0">
    <xsl:output method="xml" encoding="UTF-8"/>
    <xsl:param name="pathFile" select="src"/>
    <!-- TODO customize transformation rules 
         syntax recommendation http://www.w3.org/TR/xslt 
    -->
    <xsl:template match="/">
        <fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">
            <fo:layout-master-set>
                <fo:simple-page-master master-name="x" page-height="8.5in"
                                       page-width="11in" margin-top="0.5in" margin-bottom="0.5in"
                                       margin-left="1in" margin-right="1in">
                    <fo:region-body margin-top="1in" margin-bottom="1in"/>
                    <fo:region-before extent="0.5in"/>
                    <fo:region-after extent="0.5in"/>
                </fo:simple-page-master>
            </fo:layout-master-set>
            <fo:page-sequence master-reference="x">
                <fo:static-content flow-name="xsl-region-before">
                    <fo:block font-size="14pt" font-family="Arial"
                              space-after.optimum="15pt" text-align="center">
                        Menu
                    </fo:block>
                </fo:static-content>
                <fo:static-content flow-name="xsl-region-after">
                    <fo:block font-size="18pt" font-family="Arial"
                              line-height="24pt" space-after.optimum="15pt"
                              text-align="center" padding-top="3pt" >
                    </fo:block>
                </fo:static-content>
                <fo:flow flow-name="xsl-region-body">
                  
                    <fo:list-block>
                        <xsl:for-each select="p:menu/p:category">
                            <fo:list-item float="right">
                                <fo:list-item-label>
                                    <fo:block></fo:block>
                                </fo:list-item-label>
                                <fo:list-item-body>
                                    <fo:block> 
                                        <xsl:value-of select="p:name"/>
                                    </fo:block>
                                    <fo:block>
                                        <fo:list-block>
                                            <xsl:for-each select="p:product">
                                                <fo:list-item>
                                                    <fo:list-item-label>
                                                        <fo:block>*</fo:block>
                                                    </fo:list-item-label>
                                                    <fo:list-item-body>
                                                        <fo:block>Volvo</fo:block>
                                                    </fo:list-item-body>
                                                </fo:list-item>
                                            </xsl:for-each>
                                        </fo:list-block>
                                       
                                    </fo:block>
                                </fo:list-item-body>
                            </fo:list-item>
                        </xsl:for-each>
                    </fo:list-block>
                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>

</xsl:stylesheet>
